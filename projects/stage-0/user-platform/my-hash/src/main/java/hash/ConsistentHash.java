package hash;


import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash {
    // hash 函数接口
    private final HashFunction hashFunction;
    // 每个机器节点关联的虚拟节点个数
    private final int numberOfReplicas;
    // 环形虚拟节点
    private final SortedMap<Integer, Node> circle = new TreeMap<>();

    /**
     * @param hashFunction     hash 函数接口
     * @param numberOfReplicas 每个机器节点关联的虚拟节点个数
     * @param nodes            真实机器节点
     */
    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<Node> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        nodes.forEach(this::add);
    }

    /**
     * 增加真实机器节点
     */
    public void add(Node node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.put(this.hashFunction.hash(node.getIp() + i), node);
        }
    }

    /**
     * 删除真实机器节点
     */
    public void remove(Node node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.remove(this.hashFunction.hash(node.getIp() + i));
        }
    }

    /**
     * 取得真实机器节点
     */
    public Node get(String key) {
        if(circle.isEmpty()) {
            return null;
        }
        Integer hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, Node> tailMap = circle.tailMap(hash);// 沿环的顺时针找到一个虚拟节点
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash); // 返回该虚拟节点对应的真实机器节点的信息
    }
}

