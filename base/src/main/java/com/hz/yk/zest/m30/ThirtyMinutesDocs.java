/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.hz.yk.zest.m30;

import com.hz.yk.zest.m10.Confirmable;
import com.hz.yk.zest.m10.HasCustomer;
import com.hz.yk.zest.m10.HasLineItems;
import com.hz.yk.zest.m10.HasSequenceNumber;
import com.hz.yk.zest.m10.InventoryConcern;
import com.hz.yk.zest.m10.LineItem;
import com.hz.yk.zest.m10.PurchaseLimitConcern;
import org.qi4j.api.common.Optional;
import org.qi4j.api.concern.ConcernOf;
import org.qi4j.api.concern.Concerns;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.value.ValueBuilder;
import org.qi4j.api.value.ValueComposite;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.gt;
import static org.qi4j.api.query.QueryExpressions.templateFor;

// START SNIPPET: 6

// END SNIPPET: 6
public class ThirtyMinutesDocs {

    Module                    module;

    // START SNIPPET: 6
    @Structure
    private UnitOfWorkFactory uowFactory; // Injected
                                          // END SNIPPET: 6

    {
        // START SNIPPET: 6
        UnitOfWork uow = uowFactory.currentUnitOfWork();
        QueryBuilder<Order> builder = module.newQueryBuilder(Order.class);

        String orderNumber = "12345";
        HasSequenceNumber template = templateFor(HasSequenceNumber.class);
        builder.where(eq(template.number(), orderNumber));
        Query<Order> query = uow.newQuery(builder);

        Iterator<Order> result = query.iterator();

        if (result.hasNext()) {
            Order order = result.next();
        } else {
            // Deal with it wasn't found.
        }
        // END SNIPPET: 6
    }

    interface Report {

        void addOrderToReport(Order order);

        void addCustomerToReport(String customerName);
    }

    {
        Report report = null;
        UnitOfWork uow = module.currentUnitOfWork();

        // START SNIPPET: 7
        QueryBuilder<Order> builder = module.newQueryBuilder(Order.class);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.roll(Calendar.DAY_OF_MONTH, -90);
        Date last90days = cal.getTime();
        Order template = templateFor(Order.class);
        builder.where(gt(template.createdDate(), last90days));
        Query<Order> query = uow.newQuery(builder);

        for (Order order : query) {
            report.addOrderToReport(order);
        }
        // END SNIPPET: 7
    }

    {
        Report report = null;
        UnitOfWork uow = module.currentUnitOfWork();

        // START SNIPPET: 8
        QueryBuilder<HasCustomer> builder = module.newQueryBuilder(HasCustomer.class);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.roll(Calendar.MONTH, -1);
        Date lastMonth = cal.getTime();
        Order template1 = templateFor(Order.class);
        builder.where(gt(template1.createdDate(), lastMonth));
        Query<HasCustomer> query = uow.newQuery(builder);

        for (HasCustomer hasCustomer : query) {
            report.addCustomerToReport(hasCustomer.name().get());
        }
        // END SNIPPET: 8

    }

    public interface Order {

        void addLineItem(LineItem item);

        void removeLineItem(LineItem item);

        void completed();

        Property<Date> createdDate();
    }

    // START SNIPPET: 2
    public interface HasAuditTrail<M> {

        AuditTrail<M> auditTrail();
    }

    public interface AuditTrail<M> extends Property<List<Action<M>>> {
    }

    // alueComposite for Action means that it is among other things Immutable.
    // The Action extends a Property. We call that Property subtyping and highly recommended.
    public interface Action<T> extends ValueComposite // [2][3]
    {

        enum Type {
            added, removed, completed
        };

        /*
         * Property is a first class citizen in Zest™, instead of getters/setters naming convention to declare
         * properties.
         */
        @Optional
        Property<T> item(); // [1]

        Property<Type> action(); // [1]
    }

    public interface Trailable<M> {

        void itemAdded(M item);

        void itemRemoved(M item);

        void completed();
    }

    public class TrailableMixin<M> implements Trailable<M> {

        private @This HasAuditTrail<M> hasTrail;

        @Override
        public void itemAdded(M item) {
            addAction(item, Action.Type.added);
        }

        @Override
        public void itemRemoved(M item) {
            addAction(item, Action.Type.removed);
        }

        @Override
        public void completed() {
            addAction(null, Action.Type.completed);
        }

        private Action<M> addAction(M item, Action.Type type) {
            // The CompositeBuilder creates Immutable Action instances.
            ValueBuilder<Action> builder = module.newValueBuilder(Action.class); // [4]
            Action<M> prototype = builder.prototypeFor(Action.class);
            prototype.item().set(item);
            prototype.action().set(type);
            Action instance = builder.newInstance();
            hasTrail.auditTrail().get().add(instance);
            return instance;
        }
    }

    // END SNIPPET: 2

    // START SNIPPET: 3
    public abstract class OrderAuditTrailConcern extends ConcernOf<Order> implements Order {

        /*
         * Pay attention to the @This annotation for a type that is not present in the Composite type interface. This is
         * called a private Mixin, meaning the Mixin is only reachable from Fragments within the same Composite
         * instance.
         */
        @This
        Trailable<LineItem> trail;

        @Override
        public void addLineItem(LineItem item) {
            next.addLineItem(item);
            trail.itemAdded(item);
        }

        @Override
        public void removeLineItem(LineItem item) {
            next.removeLineItem(item);
            trail.itemRemoved(item);
        }

        @Override
        public void completed() {
            next.completed();
            trail.completed();
        }
    }

    // END SNIPPET: 3

    // START SNIPPET: 4
    /*
     * But the AuditTrail subsystem could provide a Generic Concern, that operates on a naming pattern (for instance).
     * In this case, we would move the coding of the concern from the application developer to the library developer,
     * again increasing the re-use value. It could look like this; The above construct is called a Generic Concern,
     * since it implements java.lang.reflect.InvocationHandler instead of the interface of the domain model. The
     * ConcernOf baseclass will also need to be of InvocationHandler type, and the Zest™ Runtime will handle the
     * chaining between domain model style and this generic style of interceptor call chain.
     */
    public class AuditTrailConcern extends ConcernOf<InvocationHandler> implements InvocationHandler {

        @This
        Trailable trail;

        @Override
        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            Object retValue = next.invoke(proxy, m, args);
            String methodName = m.getName();
            if (methodName.startsWith("add")) {
                trail.itemAdded(args[0]);
            } else if (methodName.startsWith("remove")) {
                trail.itemRemoved(args[0]);
            } else if (methodName.startsWith("complete") || methodName.startsWith("commit")) {
                trail.completed();
            }

            return retValue;
        }
    }

    // END SNIPPET: 4

    // START SNIPPET: 5
    @Concerns({ AuditTrailConcern.class, PurchaseLimitConcern.class, InventoryConcern.class })
    @Mixins(TrailableMixin.class)
    public interface OrderEntity extends Order, Confirmable, HasSequenceNumber, HasCustomer, HasLineItems, EntityComposite {
    }
    // END SNIPPET: 5
}
