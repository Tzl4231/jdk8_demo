package lambda_demo;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * jdk8 的lambda的使用  Lambda表达式在Java中又称为闭包或匿名函数
 * @author Tzl
 *	
 *	1.lambda表达式仅能放入如下代码：预定义使用了 @Functional 注释的函数式接口，
 *	自带一个抽象函数的方法，或者SAM（Single Abstract Method 单个抽象方法）类型。
 *	这些称为lambda表达式的目标类型，可以用作返回类型，或lambda目标代码的参数。
 *	例如，若一个方法接收Runnable、Comparable或者 Callable 接口，都有单个抽象方法，
 *	可以传入lambda表达式。类似的，如果一个方法接受声明于 java.util.function 包内的接口，
 *	例如 Predicate、Function、Consumer 或 Supplier，那么可以向其传lambda表达式。
 *
 *	2.lambda表达式内可以使用方法引用，仅当该方法不修改lambda表达式提供的参数。
 *	然而，若对参数有任何修改，则不能使用方法引用，而需键入完整地lambda表达式.
 *
 *	3.lambda内部可以使用静态、非静态和局部变量，这称为lambda内的变量捕获。
 *
 *	4.lambda表达式有个限制，那就是只能引用 final 或 final 局部变量，
 *	这就是说不能在lambda内部修改定义在域外的变量。但是只是访问它而不作修改是可以的.
 *	例如:List<Integer> aa = Arrays.asList(1,2,3);
 *		int a=2;
 *		aa.forEach((n)->a*n);这个是可以的
 *		但是:
 *		aa.forEach(n->{a++;});这个是不允许的
 */
public class LambdaDemo {
	public static void main(String[] args) {
		//1.简化之前的匿名内部类问题;
		//method1();
		//2.使用lambda进行遍历
		//method2();
		//3.lambda和predicate
		//method3();
		//4.lambda的Map和reduce(比较不错的,会真正用到的)
		//method4();
		//5.计算元素的最大值,最小值,总和,平均值
		method5();
	}
	
	/**
	 * (params)->expression
	 * (params)->statement
	 * (params)->{statements}
	 * 功能1 :简化之前的内部类的写法
	 *     如果重写的方法不对参数进行修改,只是简单的逻辑,可以写为()->System.out.println("aaaaa");
	 *     如果方法接受两个参数,那么写为(int a,int b)->a+b  通常都会把lambda表达式内部变量的名字起得短一些。这样能使代码更简短
	 * 
	 * lambda和匿名内部类区别:   
	 * 匿名类的 this 关键字指向匿名类，而lambda表达式的 this 关键字指向包围lambda表达式的类。
	 * 另一个不同点是二者的编译方式。Java编译器将lambda表达式编译成类的私有方法。
	 * 使用了Java 7的 invokedynamic 字节码指令来动态绑定这个方法 .   
	 *  
	 */
	public static void method1() {
		//1. jdk8之前的写法
		new Thread(new Runnable() {
			@Override
			public void run() {
				int a = 3;
				int b = 3;
				System.out.println("老版本匿名内部类多线程启动......"+(a+b));
			}
		}).start();
		
		//2. lambda的写法
		new Thread(()->{int a=1; int b=3; System.out.println("lambda版本的多线程启动!!!!"+(a+b));}).start();
		new Thread(()->System.out.println("lambda版本的多线程启动!!!")).start();
	}
	
	
	/**
	 * 功能2:使用lambda进行遍历
	 * 	(方法引用是lambda中使用的,操作符是::,后面会在进行分析)
	 * 	lambda的遍历方式无法使用break,continue这些关键字.
	 *  lambda中的return相当于普通遍历方式的continue.
	 *  
	 */
	private static void method2() {
		List<String> users = Arrays.asList("张三丰","张思丰","张武丰","张留丰");
		//jdk8 之前的遍历
		for (String string : users) {
			System.out.println(string);
		}
		
		//lambda方式遍历
		users.forEach(n->{
			if("张三丰".equals(n)) {
				return;
			}else {
				System.out.println(n);
			}
		});
		
		//jdk8方法引用由::双冒号操作符标示
		users.forEach(LambdaDemo::change);
		
	}
	
	/**
	 * method2使用的方法
	 * @param str
	 */
	public static void change(String str) {
		System.out.println(str+"2333");
	}
	
	
	/**
	 * lambda和函数式接口Predicate
	 *   Predicate可以使用and(),or()和xor()用于把传入filter的条件合并起来
	 *   Predicate就像是在传入if的条件
	 */
	private static void method3() {
		List<String> users = Arrays.asList("张三丰","张思丰","张武丰","张留丰");
		System.out.println("包含三的 :");
		//filter(users, (str)->str.contains("三"));
		//filter(users,(str)->true);
		//传入两个条件
		Predicate<String> condition1=(n)->n.contains("三");
		Predicate<String> condition2=(n)->n.contains("张");
		filter(users, condition1.or(condition2));
	}
	
	/**
	 * method3使用的方法
	 */
	public static void filter(List<String> names,Predicate<String> condition) {
		names.forEach(n->{
			if(condition.test(n)) {
				System.out.println(n);
			}
		});
	}
	
	
	/**
	 * lambda表达式的map和Reduce
	 * 	函数式编程概念map--它允许你将对象进行转换.
	 *  在该例子中,将n->12*n lambda表达式传入到map()方法中,就应用到了流中的每个元素
	 *  
	 *  reduce() 函数可以将所有值合并成一个。
	 *  Map和Reduce操作是函数式编程的核心操作，因为其功能，reduce 又被称为折叠操作。
	 *  另外，reduce 并不是一个新的操作，你有可能已经在使用它。
	 *  SQL中类似 sum()、avg() 或者 count() 的聚集函数，
	 *  实际上就是 reduce 操作，因为它们接收多个值并返回一个值。
	 *  流API定义的 reduceh() 函数可以接受lambda表达式，并对所有值进行合并。
	 *  IntStream这样的类有类似 average()、count()、sum() 的内建方法来做 reduce 操作，
	 *  也有mapToLong()、mapToDouble() 方法来做转换。
	 *  这并不会限制你，你可以用内建方法，也可以自己定义
	 */
	private static void method4() {
		//老方法:在不使用lambda表达式为每个值*12
		List<Integer> list = Arrays.asList(1,2,3,4,5);
		for (Integer i : list) {
			System.out.print(i*12);
		}
		System.out.println();
		//用上面学过的lambda操作
		list.forEach((i)->System.out.print(i*12));
		System.out.println();
		//使用lambda表达式中的map
		list.stream().map((n)->n*12).forEach(System.out::print);
		System.out.println();
		//===================================================
		//老方法
		List<Integer> nums = Arrays.asList(100, 200, 300, 400, 500);
		double total = 0;
		for (Integer num : nums) {
		    double price = num + 0.12*num;
		    total += price;
		}
		System.out.println("Total : " + total);
		System.out.println();
		//新方法
		double price = nums.stream().map((n)->n+0.12*n).reduce((sum,n)->sum+n).get();
		System.out.println(price);
		System.out.println();
		//========================================
		//map的使用与流的使用,将集合转换为大写,并且为,分割的字符串
		List<String> countrys = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
		String str = countrys.stream().map((n)->n.toUpperCase()).collect(Collectors.joining(","));
		System.out.println(str);
		
		//流的使用,利用流的 distinct() 方法来对集合进行去重
		List<Integer> numbers = Arrays.asList(1,2,3,5,3,2,1);
		List<Integer> disNumbers = numbers.stream().distinct().collect(Collectors.toList());
		System.out.printf("%s", disNumbers);
	}
	
	
	/**
	 * 计算集合元素的最大值,最小值,求和,平均值
	 */
	private static void method5() {
		List<Integer> ints = Arrays.asList(1,2,3,4,5,6,7,8,9);
		IntSummaryStatistics iss = ints.stream().mapToInt(n->n).summaryStatistics();
		System.out.println("最大值"+iss.getMax());
		System.out.println("最小值"+iss.getMin());
		System.out.println("平均值"+iss.getAverage());
		System.out.println("求和"+iss.getSum());
	}

	
}
