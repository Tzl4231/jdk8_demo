package lambda_demo;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * jdk8 ��lambda��ʹ��  Lambda���ʽ��Java���ֳ�Ϊ�հ�����������
 * @author Tzl
 *	
 *	1.lambda���ʽ���ܷ������´��룺Ԥ����ʹ���� @Functional ע�͵ĺ���ʽ�ӿڣ�
 *	�Դ�һ���������ķ���������SAM��Single Abstract Method �������󷽷������͡�
 *	��Щ��Ϊlambda���ʽ��Ŀ�����ͣ����������������ͣ���lambdaĿ�����Ĳ�����
 *	���磬��һ����������Runnable��Comparable���� Callable �ӿڣ����е������󷽷���
 *	���Դ���lambda���ʽ�����Ƶģ����һ���������������� java.util.function ���ڵĽӿڣ�
 *	���� Predicate��Function��Consumer �� Supplier����ô�������䴫lambda���ʽ��
 *
 *	2.lambda���ʽ�ڿ���ʹ�÷������ã������÷������޸�lambda���ʽ�ṩ�Ĳ�����
 *	Ȼ�������Բ������κ��޸ģ�����ʹ�÷������ã��������������lambda���ʽ.
 *
 *	3.lambda�ڲ�����ʹ�þ�̬���Ǿ�̬�;ֲ����������Ϊlambda�ڵı�������
 *
 *	4.lambda���ʽ�и����ƣ��Ǿ���ֻ������ final �� final �ֲ�������
 *	�����˵������lambda�ڲ��޸Ķ���������ı���������ֻ�Ƿ������������޸��ǿ��Ե�.
 *	����:List<Integer> aa = Arrays.asList(1,2,3);
 *		int a=2;
 *		aa.forEach((n)->a*n);����ǿ��Ե�
 *		����:
 *		aa.forEach(n->{a++;});����ǲ������
 */
public class LambdaDemo {
	public static void main(String[] args) {
		//1.��֮ǰ�������ڲ�������;
		//method1();
		//2.ʹ��lambda���б���
		//method2();
		//3.lambda��predicate
		//method3();
		//4.lambda��Map��reduce(�Ƚϲ����,�������õ���)
		//method4();
		//5.����Ԫ�ص����ֵ,��Сֵ,�ܺ�,ƽ��ֵ
		method5();
	}
	
	/**
	 * (params)->expression
	 * (params)->statement
	 * (params)->{statements}
	 * ����1 :��֮ǰ���ڲ����д��
	 *     �����д�ķ������Բ��������޸�,ֻ�Ǽ򵥵��߼�,����дΪ()->System.out.println("aaaaa");
	 *     �������������������,��ôдΪ(int a,int b)->a+b  ͨ�������lambda���ʽ�ڲ�������������ö�һЩ��������ʹ��������
	 * 
	 * lambda�������ڲ�������:   
	 * ������� this �ؼ���ָ�������࣬��lambda���ʽ�� this �ؼ���ָ���Χlambda���ʽ���ࡣ
	 * ��һ����ͬ���Ƕ��ߵı��뷽ʽ��Java��������lambda���ʽ��������˽�з�����
	 * ʹ����Java 7�� invokedynamic �ֽ���ָ������̬��������� .   
	 *  
	 */
	public static void method1() {
		//1. jdk8֮ǰ��д��
		new Thread(new Runnable() {
			@Override
			public void run() {
				int a = 3;
				int b = 3;
				System.out.println("�ϰ汾�����ڲ�����߳�����......"+(a+b));
			}
		}).start();
		
		//2. lambda��д��
		new Thread(()->{int a=1; int b=3; System.out.println("lambda�汾�Ķ��߳�����!!!!"+(a+b));}).start();
		new Thread(()->System.out.println("lambda�汾�Ķ��߳�����!!!")).start();
	}
	
	
	/**
	 * ����2:ʹ��lambda���б���
	 * 	(����������lambda��ʹ�õ�,��������::,������ڽ��з���)
	 * 	lambda�ı�����ʽ�޷�ʹ��break,continue��Щ�ؼ���.
	 *  lambda�е�return�൱����ͨ������ʽ��continue.
	 *  
	 */
	private static void method2() {
		List<String> users = Arrays.asList("������","��˼��","�����","������");
		//jdk8 ֮ǰ�ı���
		for (String string : users) {
			System.out.println(string);
		}
		
		//lambda��ʽ����
		users.forEach(n->{
			if("������".equals(n)) {
				return;
			}else {
				System.out.println(n);
			}
		});
		
		//jdk8����������::˫ð�Ų�������ʾ
		users.forEach(LambdaDemo::change);
		
	}
	
	/**
	 * method2ʹ�õķ���
	 * @param str
	 */
	public static void change(String str) {
		System.out.println(str+"2333");
	}
	
	
	/**
	 * lambda�ͺ���ʽ�ӿ�Predicate
	 *   Predicate����ʹ��and(),or()��xor()���ڰѴ���filter�������ϲ�����
	 *   Predicate�������ڴ���if������
	 */
	private static void method3() {
		List<String> users = Arrays.asList("������","��˼��","�����","������");
		System.out.println("�������� :");
		//filter(users, (str)->str.contains("��"));
		//filter(users,(str)->true);
		//������������
		Predicate<String> condition1=(n)->n.contains("��");
		Predicate<String> condition2=(n)->n.contains("��");
		filter(users, condition1.or(condition2));
	}
	
	/**
	 * method3ʹ�õķ���
	 */
	public static void filter(List<String> names,Predicate<String> condition) {
		names.forEach(n->{
			if(condition.test(n)) {
				System.out.println(n);
			}
		});
	}
	
	
	/**
	 * lambda���ʽ��map��Reduce
	 * 	����ʽ��̸���map--�������㽫�������ת��.
	 *  �ڸ�������,��n->12*n lambda���ʽ���뵽map()������,��Ӧ�õ������е�ÿ��Ԫ��
	 *  
	 *  reduce() �������Խ�����ֵ�ϲ���һ����
	 *  Map��Reduce�����Ǻ���ʽ��̵ĺ��Ĳ�������Ϊ�书�ܣ�reduce �ֱ���Ϊ�۵�������
	 *  ���⣬reduce ������һ���µĲ��������п����Ѿ���ʹ������
	 *  SQL������ sum()��avg() ���� count() �ľۼ�������
	 *  ʵ���Ͼ��� reduce ��������Ϊ���ǽ��ն��ֵ������һ��ֵ��
	 *  ��API����� reduceh() �������Խ���lambda���ʽ����������ֵ���кϲ���
	 *  IntStream�������������� average()��count()��sum() ���ڽ��������� reduce ������
	 *  Ҳ��mapToLong()��mapToDouble() ��������ת����
	 *  �Ⲣ���������㣬��������ڽ�������Ҳ�����Լ�����
	 */
	private static void method4() {
		//�Ϸ���:�ڲ�ʹ��lambda���ʽΪÿ��ֵ*12
		List<Integer> list = Arrays.asList(1,2,3,4,5);
		for (Integer i : list) {
			System.out.print(i*12);
		}
		System.out.println();
		//������ѧ����lambda����
		list.forEach((i)->System.out.print(i*12));
		System.out.println();
		//ʹ��lambda���ʽ�е�map
		list.stream().map((n)->n*12).forEach(System.out::print);
		System.out.println();
		//===================================================
		//�Ϸ���
		List<Integer> nums = Arrays.asList(100, 200, 300, 400, 500);
		double total = 0;
		for (Integer num : nums) {
		    double price = num + 0.12*num;
		    total += price;
		}
		System.out.println("Total : " + total);
		System.out.println();
		//�·���
		double price = nums.stream().map((n)->n+0.12*n).reduce((sum,n)->sum+n).get();
		System.out.println(price);
		System.out.println();
		//========================================
		//map��ʹ��������ʹ��,������ת��Ϊ��д,����Ϊ,�ָ���ַ���
		List<String> countrys = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
		String str = countrys.stream().map((n)->n.toUpperCase()).collect(Collectors.joining(","));
		System.out.println(str);
		
		//����ʹ��,�������� distinct() �������Լ��Ͻ���ȥ��
		List<Integer> numbers = Arrays.asList(1,2,3,5,3,2,1);
		List<Integer> disNumbers = numbers.stream().distinct().collect(Collectors.toList());
		System.out.printf("%s", disNumbers);
	}
	
	
	/**
	 * ���㼯��Ԫ�ص����ֵ,��Сֵ,���,ƽ��ֵ
	 */
	private static void method5() {
		List<Integer> ints = Arrays.asList(1,2,3,4,5,6,7,8,9);
		IntSummaryStatistics iss = ints.stream().mapToInt(n->n).summaryStatistics();
		System.out.println("���ֵ"+iss.getMax());
		System.out.println("��Сֵ"+iss.getMin());
		System.out.println("ƽ��ֵ"+iss.getAverage());
		System.out.println("���"+iss.getSum());
	}

	
}
