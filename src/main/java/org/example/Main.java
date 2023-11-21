package org.example;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Trader trader1 = new Trader("Raul", "Cambridge");
        Trader trader2 = new Trader("Mario", "Milan");
        Trader trader3 = new Trader("Alan", "Cambridge");
        Trader trader4 = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(trader4, 2011, 300));
        transactions.add(new Transaction(trader1, 2012, 1000));
        transactions.add(new Transaction(trader1, 2011, 400));
        transactions.add(new Transaction(trader2, 2012, 710));
        transactions.add(new Transaction(trader2, 2012, 700));
        transactions.add(new Transaction(trader3, 2012, 950));

        // 0. Просмотреть транзакции
        System.out.println("0. ======");
        transactions.forEach(System.out::println);

        // 1. Найти все транзакции за 2011 год и отсортировать их по сумме от большей к меньшей (меньшей к большей)
        System.out.println("1. ======");
        List<Transaction> res = transactions.stream()
                .filter(p -> p.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .toList();

//        System.out.println(res);
        res.forEach(System.out::println);

        // 2. Вывести список неповторяющтихся городов в которых работают трейдеры
        System.out.println("2. ======");
        List<String> result = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .toList();

        result.forEach(System.out::println);

        // 2.1 Вывести список неповторяющтихся городов в которых работают трейдеры
        System.out.println("2.1. ======");
        Set<String> result1 = transactions.stream()
                .map(p -> p.getTrader().getCity())
                .collect(Collectors.toSet());

        System.out.println(result1);

        // 3. Найти всех трейдеров из Кембриджа и отсортировать их по именам
        System.out.println("3. ======");
        List<Trader> r = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .toList();

        r.forEach(System.out::println);

        // 4. Вернуть строку со всеми именами трейдеров и отсортировать их в алфавитном порядке
        System.out.println("4. ======");
        List<String> traders = transactions.stream()
                .map(p -> p.getTrader())
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .map(p -> p.getName())
                .toList();

        System.out.println(traders);

        // 4.1 Вернуть строку со всеми именами трейдеров и отсортировать их в алфавитном порядке
        System.out.println("4.1. ======");
        List<String> traders1 = transactions.stream()
                .map(p -> p.getTrader().getName())
                .distinct()
                .sorted()
                .toList();

        System.out.println(traders1);

        // 4.2 Вернуть строку со всеми именами трейдеров и отсортировать их в алфавитном порядке
        System.out.println("4.2. ======");
        Optional<String> traders2 = transactions.stream()
                .map(p -> p.getTrader().getName())
                .distinct()
                .sorted()
                .reduce((x, y) -> x + "~" + y);

        System.out.println(traders2.get());

        // 4.3 Вернуть строку со всеми именами трейдеров и отсортировать их в алфавитном порядке
        System.out.println("4.3. ======");
        String traders3 = transactions.stream()
                .map(p -> p.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("-> ", (x, y) -> x + "~" + y);

        System.out.println(traders3);

        // 4.4 Вернуть строку со всеми именами трейдеров и отсортировать их в алфавитном порядке
        System.out.println("4.4. ======");
        String traders4 = transactions.stream()
                .map(p -> p.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining("~~", "<", ">"));

        System.out.println(traders4);

        // 5. Выяснить существует ли хотябы одн трейдер из Милана
        System.out.println("5. ======");
        long count = transactions.stream()
                .filter(p -> p.getTrader().getCity().equals("Milan"))
                .count();
        System.out.println(count != 0);

        // 5.1 Выяснить существует ли хотябы одн трейдер из Милана
        System.out.println("5.1. ======");
        boolean ok = transactions.stream()
                .anyMatch(p -> p.getTrader().getCity().equals("Milan"));
        System.out.println(ok);

        // 6. Вывести сумму всех транзакций из Кембриджа
        System.out.println("6. ======");
        int sum = transactions.stream()
                .filter(p -> p.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, (x, y) -> x + y);

        System.out.println(sum);

        // 6.1 Вывести сумму всех транзакций из Кембриджа
        System.out.println("6.1. ======");
        int sum1 = transactions.stream()
                .filter(p -> p.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);

        System.out.println(sum1);

        // 6.2 Вывести сумму всех транзакций из Кембриджа
        System.out.println("6.2. ======");
        int sum11 = transactions.stream()
                .filter(p -> p.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .mapToInt(x -> x)
                .sum();

        System.out.println(sum11);

        // 6.3 Вывести суммЫ всех транзакций из Кембриджа
        System.out.println("6.3. ======");
        List<Integer> sums = transactions.stream()
                .filter(p -> p.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .toList();

        System.out.println(sums);
        System.out.println("-----");

        var agg = transactions.stream()
                .filter(p -> p.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.groupingBy(Transaction::getTrader))
                .values().stream()
                .map(x -> x.stream().map(Transaction::getValue).reduce(0, Integer::sum))
                .toList();

        System.out.println(agg);


        // 7. Какова максимальная сумма всех тиранзакций
        System.out.println("7. =====");
        Optional<Integer> max = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .map(Transaction::getValue)
                .findFirst();

        max.ifPresent(System.out::println);

        // 7.1 Какова максимальная сумма всех тиранзакций
        System.out.println("7.1. ======");
        Optional<Integer> max1 = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        max1.ifPresent(System.out::println);

        // 7.2 Какова максимальная сумма всех тиранзакций
        System.out.println("7.2. ======");
        OptionalInt max2 = transactions.stream()
                .map(Transaction::getValue)
                .mapToInt(x -> x)
                .max();

//        System.out.println(max2.getAsInt());
        max2.ifPresent(System.out::println);

        // 8. Extra: factorial (BI)
        System.out.println("8. ======");
        BigInteger bi = new BigInteger("1234569789870001", 10);
//        BigInteger bi = new BigInteger("10000000000000100000001111111111111111100000001", 2);
        System.out.println(bi);

        Stream<BigInteger> biStream = Stream.iterate(1, p -> p + 1)
                .limit(20000)
                .map(BigInteger::valueOf);


//        long startTime = System.currentTimeMillis();
        long startTime = System.nanoTime();

        Optional<BigInteger> factBi = biStream
                .parallel()
                .reduce(BigInteger::multiply);

        System.out.println("Duration=" + (System.nanoTime() - startTime));

//        System.out.println("fac: " + factBi.get());
        factBi.ifPresent(p -> {
            System.out.println("fac: " + p);
        });


        Stream<Integer> numStream = Stream.iterate(1, p -> p + 1)
                .limit(20)
                .skip(1);

        long fact = numStream
                .mapToLong(x -> x)
                .reduce(1L, (x, y) -> x * y);
        System.out.println("fac: " + fact);

        // 9. Extra. Проверить "качество распределения" Math.random()
        System.out.println("9. ======");
        Map<Integer, Integer> map = IntStream.generate(() -> (int) (Math.random() * 10))
                .limit(1000000)
                .boxed()
                .collect(Collectors.toMap(p -> p, p -> 1, (x, y) -> x + y));

        System.out.println(map);
        map.entrySet().forEach(System.out::println);
        System.out.println("======");

    }
}