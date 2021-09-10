import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainSpring {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
        SignUpService signUpService = applicationContext.getBean(SignUpService.class);
        signUpService.singUp("marsel.y02@mail.ru","qwerty007");
    }
}
