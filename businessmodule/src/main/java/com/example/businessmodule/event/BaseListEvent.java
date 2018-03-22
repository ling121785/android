package com.example.businessmodule.event;


import com.example.businessmodule.rest.PageRequest;

/**
 * 列表请求事件基类
 */
public abstract class BaseListEvent<T> extends BaseEvent<BaseListEvent.Request, T> {

    public  static class Request extends PageRequest {
        private transient boolean fromeServer = true;
        private transient long startTime = -1;
        private transient long endTime = -1;

        public Request() {
            super();
        }

        public Request(int size, int curPage) {
            super(curPage,size);
        }

        public Request(int size, int curPage, long startTime, long endTime) {
            super(curPage,size);
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Request(boolean fromeServer) {
            super();
            this.fromeServer = fromeServer;
        }

        public Request(int size, int curPage, boolean fromeServer) {
            super(curPage,size);
            this.fromeServer = fromeServer;
        }

        public Request(int size, int curPage, long startTime, long endTime, boolean fromeServer) {
            super(curPage,size);
            this.startTime = startTime;
            this.endTime = endTime;
            this.fromeServer = fromeServer;
        }

        public boolean isFromeServer() {
            return fromeServer;
        }



        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setFromeServer(boolean fromeServer) {
            this.fromeServer = fromeServer;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }

    public BaseListEvent(long eventId) {
        super(eventId);
        setRequest(new Request());
    }

    public BaseListEvent(long eventId, int listSize, int cursor) {
        super(eventId);
        setRequest(new Request(listSize, cursor));
    }

    public BaseListEvent(long eventId, int listSize, int cursor, long startTime, long endTime) {
        super(eventId);
        setRequest(new Request(listSize, cursor, startTime, endTime));
    }

    public BaseListEvent(long eventId,Request request){
        super(eventId);
        setRequest(request);
    }

    public BaseListEvent(long eventId, boolean fromeServer) {
        super(eventId);
        setRequest(new Request(fromeServer));
    }

    public BaseListEvent(long eventId, int listSize, int cursor, boolean fromeServer) {
        super(eventId);
        setRequest(new Request(listSize, cursor, fromeServer));
    }

    public BaseListEvent(long eventId, int listSize, int cursor, long startTime, long endTime, boolean fromeServer) {
        super(eventId);
        setRequest(new Request(listSize, cursor, startTime, endTime, fromeServer));
    }
}
