package hash;


import java.math.BigDecimal;
import java.util.*;

public class HashTest {

    // 机器节点IP前缀
    private static final String IP_PREFIX = "192.168.1.";

    // 方差
    public static double Variance(int[] x) {
        int m = x.length;
        double sum = 0;
        //求和
        for(double value : x) {
            sum += value;
        }
        //求平均值
        double dAve = sum / m;
        double dVar = 0;
        //求方差
        for(double v : x) {
            dVar += (v - dAve) * (v - dAve);
        }
        return dVar / m;
    }

    /**
     * 标准差
     * @param x
     * @return
     */
    public static double StandardDivination(int[] x) {
        return Math.sqrt(Variance(x));
    }

    public static void main(String[] args) {
        // 每台真实机器节点上保存的记录条数
        Map<String, Integer> map = new HashMap<>();
        HashFunction hashFunction = new HashFunctionImpl();
        LinkedHashMap<Integer, Double> variances = new LinkedHashMap<>();

        for (int numberOfReplicas = 100; numberOfReplicas <= 1000; numberOfReplicas += 10) {
            System.out.println("单个机器的虚拟节点: " + numberOfReplicas);
            map.clear();
            //真实的10个物理节点
            List<Node> realNodes = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                // 每台真实机器节点上保存的记录条数初始为0
                map.put(IP_PREFIX + i, 0);
                Node node = new Node(IP_PREFIX + i, "node" + i);
                realNodes.add(node);
            }
            ConsistentHash consistentHash = new ConsistentHash(hashFunction, numberOfReplicas, realNodes);
            // 将1000000条记录尽可能均匀的存储到10台机器节点
            for (int i = 0; i < 1000000; i++) {
                // 产生随机一个字符串当做一条记录，可以是其它更复杂的业务对象,比如随机字符串相当于对象的业务唯一标识
                String data = UUID.randomUUID().toString() + i;
                // 通过记录找到真实机器节点
                Node node = consistentHash.get(data);
                // 每台真实机器节点上保存的记录条数加1
                map.put(node.getIp(), map.get(node.getIp()) + 1);
            }
            // 打印每台真实机器节点保存的记录条数
            int[] numbers = new int[10];
            for (int i = 1; i <= 10; i++) {
                numbers[i - 1] = map.get("192.168.1." + i);
                System.out.println(IP_PREFIX + i + "节点记录条数：" + map.get("192.168.1." + i));
            }
            System.out.println(String.format("方差: %s", BigDecimal.valueOf(Variance(numbers))));
            System.out.println(String.format("标准差: %s", BigDecimal.valueOf(StandardDivination(numbers))));
            variances.put(numberOfReplicas, StandardDivination(numbers));
            System.out.println();
            System.out.println();
        }
        System.out.println(variances);
    }
}
