package cn.bugstack.xfg.dev.tech;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@RestController()
@RequestMapping("/api/")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    // 创建线程池
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    // 用于存储内存占用对象的列表
    private static final List<Object> memoryHolder = new ArrayList<>();

    /**
     * http://localhost:8091/api/exec
     */
    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    public String exec() {
        executorService.submit(() -> {
            try {
                // 创建一个大对象
//                    byte[] largeObject = new byte[10 * 1024 * 1024]; // 10MB
                byte[] largeObject = new byte[10 * 1024]; // 10MB

                // 将其加入内存占用列表中，防止被gc
                synchronized (memoryHolder) {
                    memoryHolder.add(largeObject);
                }

                log.info("模拟填充大对象");

                // 模拟一些工作
                Thread.sleep(1000);

            } catch (OutOfMemoryError e) {
                System.err.println("Out of memory! Halting further allocation.");
                // 如果出现OOM，暂停分配以防止程序崩溃
                executorService.shutdown();

            } catch (InterruptedException e) {
                // 捕获并处理睡眠中断
                Thread.currentThread().interrupt();
                System.err.println("Task interrupted");
            }
        });
        return "ok";
    }

}
