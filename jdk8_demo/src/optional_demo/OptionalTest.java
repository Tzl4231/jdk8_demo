package optional_demo;

import java.util.Optional;

/**
 * @author Tzl
 * Optional���ʹ��
 */
public class OptionalTest {
	public static void main(String[] args) {
		//���ù���ģʽ����Optional��
		Optional<User> user = Optional.of(new User());
	}
}


/**
 * 
 * ����ʹ��pojo��
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