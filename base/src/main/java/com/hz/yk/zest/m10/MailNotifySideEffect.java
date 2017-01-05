/*
 * Copyright (c) 2012, Niclas Hedhman. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *     You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hz.yk.zest.m10;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.sideeffect.SideEffectOf;

import com.hz.yk.zest.m10.elsewhere.mail.MailService;

/**
 * By not referencing the aggregated interface OrderEntity, we reduce the coupling of this SideEffect and it can be used
 * in any other Composite where the HasCustomer and HasLineItems combination is used, for instance in an InvoiceEntity.
 */
public abstract class MailNotifySideEffect extends SideEffectOf<Confirmable> implements Confirmable {

    @Service
    private MailService  mailer;

    // @This is telling Zest™ that the SideEffect needs a reference to the Composite instance that it belongs to.
    // @This 告诉zest SideEffect 需要组合实例的引用
    @This
    private HasLineItems hasItems;

    @This
    private HasCustomer  hasCustomer;

    @Override
    public void confirm() {
        StringBuilder builder = new StringBuilder();
        builder.append("An Order has been made.\n\n\n");
        builder.append("Customer:");
        builder.append(hasCustomer.name().get());
        builder.append("\n\nItems ordered:\n");
        for (LineItem item : hasItems.lineItems().get()) {
            builder.append(item.name().get());
            builder.append(" : ");
            builder.append(item.quantity().get());
            builder.append("\n");
        }
        mailer.send("sales@mycompany.com", builder.toString());
    }
}
