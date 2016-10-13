package com.uv.event.impl;

import com.uv.event.EventHandlerQueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by uv2sun on 15/11/12.
 */
public class EventHandlerQueueImpl<E> implements EventHandlerQueue<E> {
    private Collection<E> collection;

    public EventHandlerQueueImpl() {
        this.collection = new ConcurrentLinkedQueue<>();
    }

    @Override
    public int size() {
        return collection.size();
    }


    @Override
    public Iterator<E> iterator() {
        return collection.iterator();
    }

    @Override
    public void add(E e) {
        collection.add(e);
    }

    @Override
    public void remove(E e) {
        collection.remove(e);
    }
}
