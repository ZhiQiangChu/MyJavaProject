package com.hz.yk.io.generic.impl;

import com.hz.yk.io.generic.Output;
import com.hz.yk.io.generic.Sender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author wuzheng.yk
 *         Date: 13-4-5
 *         Time: 下午4:01
 */
public class FileOutput implements Output<String, IOException> {
    final File destination;
    final Writer writer;

    public FileOutput(File destination) throws IOException {
        this.destination = destination;
        writer = new FileWriter(destination);
    }

    @Override
    public <SenderThrowableType extends Throwable> void receiveFrom(Sender<String, SenderThrowableType> sender)
            throws IOException, SenderThrowableType {
        final StringReceiver receiver = new StringReceiver(writer);
        sender.sendTo(receiver);
        receiver.finished();
        try {
            writer.close();
        } catch (Exception e) {
            // ignore close exception
        }
    }
}
