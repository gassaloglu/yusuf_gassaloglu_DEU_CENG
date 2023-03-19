package columns;

public class NodeDll {
    private Object data;
    private NodeDll prev, next;


    public NodeDll(Object dataToAdd) {
        this.data = dataToAdd;
        next = null;
        prev = null;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public NodeDll getPrev() {
        return prev;
    }
    public void setPrev(NodeDll prev) {
        this.prev = prev;
    }

    public NodeDll getNext() {
        return next;
    }
    public void setNext(NodeDll next) {
        this.next = next;
    }
}
