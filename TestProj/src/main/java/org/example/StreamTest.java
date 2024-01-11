package org.example;

import java.util.*;

public class StreamTest {
    public static void main(String[] args) {
        test1();
    }
    public static void test1() {
        Random random = new Random();
//        int[] array = random.ints(10, 20).limit(5).toArray();
//        for (int i : array) {
//            System.out.println(i);
//        }
// 在sorted中,return 1 代表向后,return -1代表向前, return 0代表保持原始顺序
        List<Integer> list2 = random.ints(10, 20)
                .limit(5).boxed().toList();
        list2.stream()
                .sorted((a, b) -> {
                    if (a % 2 == 0 && b % 2 != 0) {
                        return 1; // 偶数在后面
                    } else if (a % 2 != 0 && b % 2 == 0) {
                        return -1; // 奇数在前面
                    } else {
                        return 0; // 保持原始顺序
                    }
                })
                .forEach(System.out::println);

        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("name", "Alice");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 3);
        map2.put("name", "Bob");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 2);
        map3.put("name", "Charlie");
        list.add(map3);
        list.sort(Comparator.comparing(m -> (Integer) m.get("id"), Comparator.reverseOrder()));
        List<Map<String, Object>> name = list.stream().map(item -> {
            item.replace("name", item.get("name")+item.get("id").toString());
            return item;
        }).toList();
        System.out.println("Map后 = " + name);
//        list.forEach(System.out::println);
    }
}
