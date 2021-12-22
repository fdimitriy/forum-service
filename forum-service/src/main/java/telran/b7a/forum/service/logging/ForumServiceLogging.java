package telran.b7a.forum.service.logging;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Slf4j (topic = "Forum Service") // можно поставить свое название, будет выведено в логах
@Service
public class ForumServiceLogging {
	
	@Pointcut("execution(public java.util.List telran.b7a.forum.service.ForumServiceImpl.find*(..))") // либо вместо java.util.List, указать *
	public void bulkingFind() {}
	
	@Pointcut("execution(public * telran.b7a.forum.service.ForumServiceImpl.findPostById(String))" + " && args(id)")
	public void findPostById(String id) {}
	
	@Pointcut("@annotation(PostLogger) && args(.., id)")
	public void annotated(String id) {}
	
	@Around("bulkingFind()")
	public Object bulkingFindLogging(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs(); // все аргументы вызываемого метода
		long t1 = System.currentTimeMillis();
		Object retVal = pjp.proceed(args);		
		long t2 = System.currentTimeMillis();
		log.info("method - {}, duration = {}", pjp.getSignature().toLongString(), (t2 - t1));
		return retVal;
	}
	
	@Before("findPostById(id)")
	public void signLogging(String id) {
		log.info("post with id{} requested ",  id);
	}
	
	@AfterReturning("annotated(id)")
	public void updatePostLoggin(String id) {
		log.info("post with id {} updated", id);		
	}	

}
