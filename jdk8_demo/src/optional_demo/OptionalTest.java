package optional_demo;

import java.util.Optional;

/**
 * @author Tzl
 * Optional类的使用
 */
public class OptionalTest {
	public static void main(String[] args) {
		//调用工厂模式创建Optional类
		Optional<User> user = Optional.of(new User());
	}
}


/**
 * 
 * 测试使用pojo类
 * @author tzl
 *
 */
class User{
	private String name;
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}