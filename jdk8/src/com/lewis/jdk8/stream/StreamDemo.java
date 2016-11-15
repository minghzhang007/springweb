package com.lewis.jdk8.stream;

import com.lewis.jdk8.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zhangminghua on 2016/11/14.
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<Student> students = getStudents();
        List<Student> wuhanlist = students.stream().filter(s -> s.getSchool().equals("武汉大学")).collect(Collectors.toList());
        wuhanlist.stream().forEach(System.out::println);
        List<Integer> numbers = getIntegers();
        //distinct
        numbers.stream().filter(i -> i%2 ==0).distinct().forEach(i -> System.out.print(i+" "));
        System.out.println();
        //limit
        students.stream().filter(s -> s.getMajor().equals("土木工程")).limit(2).forEach(System.out::println);
        //排序 sorted
        students.stream().filter(s -> s.getMajor().equals("土木工程")).sorted((s1,s2) -> s1.getAge() - s2.getAge()).limit(2).forEach(System.out::println);
        System.out.println();
        //skip
        students.stream().filter(s -> s.getMajor().equals("土木工程")).skip(2).forEach(System.out::println);
        //map
        List<String> nameList = students.stream().filter(s -> "计算机科学".equals(s.getMajor())).map(Student::getName).collect(Collectors.toList());
        nameList.stream().forEach(System.out::println);
        //mapToInt
        int totalAge = students.stream().filter(s -> "计算机科学".equals(s.getMajor())).mapToInt(Student::getAge).sum();
        System.out.println(totalAge);
        String[] strs = {"java8", "is", "easy", "to", "use"};
        List<String[]> list = Arrays.stream(strs).map(s -> s.split("")).distinct().collect(Collectors.toList());
        list.stream().forEach(System.out::println);
        //flatMap
        List<String> listString = Arrays.stream(strs).map(s -> s.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        listString.stream().forEach(System.out::print);
        System.out.println();
        //allMatch
        boolean ageAllMatch = students.stream().allMatch(s -> s.getAge() > 18);
        System.out.println(ageAllMatch);
        //anyMatch
        boolean schoolAnyMatch = students.stream().anyMatch(s -> s.getSchool().equals("北京大学"));
        System.out.println(schoolAnyMatch);
        //noneMatch
        System.out.println(students.stream().noneMatch(s -> s.getAge() > 30));
        //findFirst
        students.stream().filter(s -> s.getSchool().equals("华中科技大学")).findFirst().ifPresent(System.out::println);
        //findAny
        students.stream().filter(s -> s.getSchool().equals("华中科技大学")).findAny().ifPresent(System.out::println);
        //reduce
        Integer age = students.stream().filter(s -> s.getSchool().equals("华中科技大学")).map(Student::getAge).reduce(0, (a, b) -> a + b);
        System.out.println(age);
        Integer age1 = students.stream().filter(s -> s.getSchool().equals("华中科技大学")).map(Student::getAge).reduce(0, Integer::sum);
        System.out.println(age1);
        //无默认值的reduce
        Optional<Integer> optioanl = students.stream().filter(s -> s.getSchool().equals("华中科技大学")).map(Student::getAge).reduce(Integer::sum);
        if (optioanl.isPresent()) {
            System.out.println(optioanl.get());
        }

        Long count = students.stream().collect(Collectors.counting());
        System.out.println(count);
        long count1 = students.stream().count();
        System.out.println(count1);

        //求最大年龄
        Optional<Student> so = students.stream().collect(Collectors.maxBy((s1, s2) -> s1.getAge() - s2.getAge()));
        System.out.println(so);
        //进一步简化
        students.stream().collect(Collectors.maxBy(Comparator.comparing(Student::getAge))).ifPresent(System.out::println);
        //求最小年龄
        students.stream().collect(Collectors.minBy(Comparator.comparing(Student::getAge))).ifPresent(System.out::println);
        //求年龄总和
        System.out.println("========================");
        System.out.println("========================");
        IntSummaryStatistics intSummaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.out.println(intSummaryStatistics.getSum());
        System.out.println(intSummaryStatistics.getAverage());
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getCount());
        //字符串拼接
        String name = students.stream().map(Student::getName).collect(Collectors.joining());
        System.out.println(name);
        System.out.println(students.stream().map(Student::getName).collect(Collectors.joining(",")));
        //分组
        Map<String, List<Student>> group1 = students.stream().collect(Collectors.groupingBy(Student::getSchool));
        System.out.println(group1.toString());
        //多级分组
        Map<String, Map<String, List<Student>>> map = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.groupingBy(Student::getMajor)));
        System.out.println(map.toString());
        //groupingBy的第二个参数不是只能传递groupingBy，还可以传递任意Collector类型，比如我们可以传递一个Collector.counting，用以统计每个组的个数：
        Map<String, Long> collect = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.counting()));
        System.out.println(collect.toString());
        //分区操作
        Map<Boolean, List<Student>> map1 = students.stream().collect(Collectors.partitioningBy(s -> s.getSchool().equals("武汉大学")));
        System.out.println(map1);
        IntStream.range(0,100).forEach(x ->System.out.print(x+" "));
    }

    public static List<Student> getStudents(){
        return new ArrayList<Student>() {
            {
                add(new Student(20160001, "孔明", 20, 1, "土木工程", "武汉大学"));
                add(new Student(20160002, "伯约", 21, 2, "信息安全", "武汉大学"));
                add(new Student(20160003, "玄德", 22, 3, "经济管理", "武汉大学"));
                add(new Student(20160004, "云长", 21, 2, "信息安全", "武汉大学"));
                add(new Student(20161001, "翼德", 21, 2, "机械与自动化", "华中科技大学"));
                add(new Student(20161002, "元直", 23, 4, "土木工程", "华中科技大学"));
                add(new Student(20161003, "奉孝", 23, 4, "计算机科学", "华中科技大学"));
                add(new Student(20162001, "仲谋", 22, 3, "土木工程", "浙江大学"));
                add(new Student(20162002, "鲁肃", 23, 4, "计算机科学", "浙江大学"));
                add(new Student(20163001, "丁奉", 24, 5, "土木工程", "南京大学"));
            }
        };
    }

    public static List<Integer> getIntegers(){
        return new ArrayList<Integer>(){
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
                add(7);
                add(8);
                add(8);
                add(9);
                add(10);
            }
        };
    }

    public static void test(){
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        Stream.of(letters).map(String::toUpperCase).forEach(System.out::println);
        String str = "hello ! china ! my mama !";
        Stream.of(str).flatMap(w -> characterStream(w)).forEach(System.out::print);
        System.out.println();
        Stream.generate(Math::random).limit(100).map(x -> x * 100).filter(x -> x.intValue() >= 50).forEach(x -> System.out.println(x));
        Object[] objects = Stream.iterate(1.0, p -> p * 2).
                peek(e -> System.out.println("Fetch " + e)).
                limit(20).toArray();
        Optional<String> maxOptional = Stream.of(letters).max(String::compareToIgnoreCase);
        if (maxOptional.isPresent()) {
            System.out.println(maxOptional.get());
        }
        maxOptional.ifPresent(System.out::println);
        List<String> list = new ArrayList<>();
        maxOptional.map(x -> list.add(x));
        maxOptional.map(list::add);
        System.out.println("====");
        list.stream().forEach(System.out::println);
        String s = maxOptional.orElse("");
        System.out.println(s);
        int[] intarray = {1, 2, 3, 4, 5, 6};
        Integer[] integers = Stream.of(intarray).toArray(Integer[]::new);
    }


    public static Stream<Character> characterStream(String str) {
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }
}
