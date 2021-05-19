package hash;

public class Node {
    private String ip;
    private String name;
    public Node(String ip, String name){
        this.ip = ip;
        this.name = name;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
