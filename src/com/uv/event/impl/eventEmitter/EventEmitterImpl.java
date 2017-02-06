package com.uv.event.impl.eventEmitter;

import com.uv.event.*;
import com.uv.event.impl.executor.EventExecutorSplitImpl;
import com.uv.event.impl.queue.EventHandlerQueueImpl;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by uv2sun on 15/11/10.
 * 事件容器
 */
public class EventEmitterImpl implements EventEmitter {
    private static Log log = LogFactory.getLog(EventEmitterImpl.class);
    /**
     * 事件池
     */
    private Map<String, EventHandlerQueue<Object>> eventPool;
    /**
     * 事件默认执行器
     */
    private EventExecutor eventExecutor;

    /**
     * 下面两个map用于处理数据分发时
     * eventSpliterMap<EventName, EventSpliter>
     * 不同事件名称的分发规则接口。
     * splitedEventExecutorMap<eventName, Map<key, EventExecutor>> key为事件的分发规则接口实现split返回的key
     * 根据分发规则确定某一个事件每一路key的执行器。执行器默认都是一个线程的ExecutorService，实现为EventExecutorSplitImpl
     */
    private Map<String, EventSpliter> eventSpliterMap = new HashMap<>();
    private Map<String, Map<Object, EventExecutor>> splitedEventExecutorMap = new HashMap<>();
    /**
     * 事件每个分发通道的事件处理队列copy
     * splitedEventHandlerQueueMap<eventName, Map<key, EventHandlerQueue>>
     * key为事件的分发规则接口实现split返回的key
     */
    private Map<String, Map<Object, EventHandlerQueue<Object>>> splitedEventHandlerQueueMap = new HashMap<>();

    @Override
    public void on(String eventName, EventHandler eventHandler) {
        log.debug("on [" + eventName + "]EventHandlerID:" + eventHandler.getEventHandlerID());
        if (eventName != null && eventName.length() > 0 && eventHandler != null) {
            //给事件处理器设置其所在事件容器
            eventHandler.setEventEmitter(this);
            //给事件处理器添加处理事件的名称
            eventHandler.setEventName(eventName);
            addEventToPool(eventName, eventHandler);
//            if (this.eventPool.containsKey(eventName)) { //如果已经存在当前事件
//                EventHandlerQueue list = this.eventPool.get(eventName);
//                if (list == null) {//事件存在,但是事件处理器容器为null
//                    list = new EventHandlerQueueImpl<>();
//                    this.eventPool.put(eventName, list);//重新添加
//                }
//                list.add(eventHandler);//事件处理器添加进容器
//            } else {//不存在当前事件
//                EventHandlerQueue list = new EventHandlerQueueImpl<>();
//                list.add(eventHandler);
//                this.eventPool.put(eventName, list);
//            }
        }
        log.debug("eventPool.keySet:" + eventPool.keySet());
    }

    @Override
    public void on(String eventName, Class<? extends EventHandler> eventHandlerClass) {
        Class[] cs = eventHandlerClass.getInterfaces();
        System.out.println(cs.length);
        for (int i = 0; i < cs.length; i++) {
            System.out.println(cs[i]);
        }
        log.debug("on [" + eventName + "]EventHandlerClass:" + eventHandlerClass);
        if (eventName != null && eventName.length() > 0 && eventHandlerClass != null) {
            addEventToPool(eventName, eventHandlerClass);
        }
        log.debug("eventPool.keySet:" + eventPool.keySet());
    }

    @Override
    public void setEventSpliter(String eventName, EventSpliter eventSpliter) {
        this.eventSpliterMap.put(eventName, eventSpliter);
    }


    @Override
    public void remove(String eventName) {
        System.out.println("remove all " + eventName);
        if (eventName != null && eventName.length() > 0) {
            this.eventPool.remove(eventName);
        }
    }

    @Override
    public void remove(String eventName, EventHandler eventHandler) {
        System.out.println("remove " + eventName + ":" + eventHandler.getEventHandlerID());
        EventHandlerQueue queue = getEventSequence(eventName);
        if (queue != null && queue.size() > 0) {
            queue.remove(eventHandler);
        }
    }

    @Override
    public void remove(String eventName, Class<EventHandler> eventHandlerClass) {
        System.out.println("remove " + eventName + ":" + eventHandlerClass);
        EventHandlerQueue queue = getEventSequence(eventName);
        if (queue != null && queue.size() > 0) {
            queue.remove(eventHandlerClass);
        }
    }


    @Override
    public void trigger(String eventName, JSONObject data) throws RejectedExecutionException {
        if (this.eventSpliterMap.containsKey(eventName)) {
            //计算分发key值
            Object key = this.eventSpliterMap.get(eventName).split(data);
            //处理分发key对应执行器
            if (!this.splitedEventExecutorMap.containsKey(eventName)) {
                this.splitedEventExecutorMap.put(eventName, new HashMap<Object, EventExecutor>());
            }
            Map<Object, EventExecutor> executorMap = this.splitedEventExecutorMap.get(eventName);
            if (!executorMap.containsKey(key)) {
                executorMap.put(key, new EventExecutorSplitImpl(eventName, key));
            }

            //处理分发key对应的时间处理器队列copy
            if (!this.splitedEventHandlerQueueMap.containsKey(eventName)) {
                this.splitedEventHandlerQueueMap.put(eventName, new HashMap<Object, EventHandlerQueue<Object>>());
            }
            Map<Object, EventHandlerQueue<Object>> handlerQueueMap = this.splitedEventHandlerQueueMap.get(eventName);
            if (!handlerQueueMap.containsKey(key)
                    || handlerQueueMap.get(key).size() != this.getEventSequence(eventName).size()) {
                EventHandlerQueue<Object> queue = new EventHandlerQueueImpl<>();
                //事件队列copy
                for (Iterator it = this.getEventSequence(eventName).iterator(); it.hasNext(); ) {
                    Object o = it.next();
                    if (o instanceof Class) {
                        queue.add(o);
                    } else {
                        try {
                            queue.add(o.getClass().newInstance());
                        } catch (InstantiationException | IllegalAccessException e) {
                            log.error("事件队列copy失败，[" + o + "]", e);
                        }
                    }
                }
                handlerQueueMap.put(key, queue);
            }
            executorMap.get(key).exec(eventName, handlerQueueMap.get(key), data);
        } else {
            this.eventExecutor.exec(eventName, this.getEventSequence(eventName), data);
        }
        /**
         * 如果此事件名称对应的处理器列表已经为空,则删除此事件
         */
//        if (this.getEventSequence(eventName).size() == 0) {
//            this.remove(eventName);
//        }
    }

    @Override
    public void trigger(String eventName) throws RejectedExecutionException {
        this.trigger(eventName, null);
    }

    @Override
    public EventExecutor getExecutor() {
        return this.eventExecutor;
    }

    @Override
    public Map<String, EventHandlerQueue<Object>> getEventPool() {
        return this.eventPool;
    }

    /**
     * 从事件池提取对应时间名称的事件容器(列表)
     *
     * @param eventName
     * @return
     */
    public EventHandlerQueue getEventSequence(String eventName) {
        return this.eventPool.get(eventName);
    }

    public EventEmitterImpl(EventExecutor eventExecutor) {
        this.eventPool = new HashMap<>();
        this.eventExecutor = eventExecutor;
    }

    /**
     * 将ehOrClass(eventHandler实例或者类)添加到event Pool
     *
     * @param eventName
     * @param ehOrClass
     */
    private void addEventToPool(String eventName, Object ehOrClass) {
        if (this.eventPool.containsKey(eventName)) { //如果已经存在当前事件
            EventHandlerQueue list = this.eventPool.get(eventName);
            if (list == null) {//事件存在,但是事件处理器容器为null
                list = new EventHandlerQueueImpl<>();
                this.eventPool.put(eventName, list);//重新添加
            }
            list.add(ehOrClass);//事件处理器添加进容器
        } else {//不存在当前事件
            EventHandlerQueue list = new EventHandlerQueueImpl<>();
            list.add(ehOrClass);
            this.eventPool.put(eventName, list);
        }
    }

    @Override
    public String toString() {
        String info = this.getExecutor().toString();
        String[] eventNames = new String[this.splitedEventExecutorMap.size()];
        eventNames = this.splitedEventExecutorMap.keySet().toArray(eventNames);
        for (int i = 0; i < eventNames.length; i++) {
            Map<Object, EventExecutor> tm = this.splitedEventExecutorMap.get(eventNames[i]);
            Object[] keys = tm.keySet().toArray();
            for (int j = 0; j < keys.length; j++) {
                info += "\n" + tm.get(keys[i]).toString();
            }
        }
        return info;
    }
}
