package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for cross-cutting concerns in the Library Management System
 */
@Aspect
@Component
public class LibraryAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LibraryAspect.class);

    // Pointcut for all methods in service package
    @Pointcut("execution(* com.library.service.*.*(..))")
    public void serviceLayer() {}

    // Pointcut for all methods in repository package
    @Pointcut("execution(* com.library.repository.*.*(..))")
    public void repositoryLayer() {}

    // Pointcut for all methods in controller package
    @Pointcut("execution(* com.library.controller.*.*(..))")
    public void controllerLayer() {}

    // Before advice for service methods
    @Before("serviceLayer()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        logger.info("Entering service method: {}.{}", 
                   joinPoint.getTarget().getClass().getSimpleName(),
                   joinPoint.getSignature().getName());
        
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logger.debug("Method arguments: {}", java.util.Arrays.toString(args));
        }
    }

    // After returning advice for service methods
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Successfully completed service method: {}.{}", 
                   joinPoint.getTarget().getClass().getSimpleName(),
                   joinPoint.getSignature().getName());
        
        if (result != null) {
            logger.debug("Method returned: {}", result.toString());
        }
    }

    // After throwing advice for service methods
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
    public void logServiceException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in service method: {}.{} - Error: {}", 
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    exception.getMessage());
    }

    // Around advice for repository methods (performance monitoring)
    @Around("repositoryLayer()")
    public Object monitorRepositoryPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        String methodName = joinPoint.getTarget().getClass().getSimpleName() + "." + 
                           joinPoint.getSignature().getName();
        
        logger.debug("Starting repository method: {}", methodName);
        
        try {
            Object result = joinPoint.proceed();
            
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Repository method {} completed in {} ms", methodName, executionTime);
            
            if (executionTime > 1000) {
                logger.warn("Slow repository method detected: {} took {} ms", methodName, executionTime);
            }
            
            return result;
            
        } catch (Throwable e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Repository method {} failed after {} ms with error: {}", 
                        methodName, executionTime, e.getMessage());
            throw e;
        }
    }

    // Before advice for controller methods (request logging)
    @Before("controllerLayer()")
    public void logControllerRequest(JoinPoint joinPoint) {
        String controllerMethod = joinPoint.getTarget().getClass().getSimpleName() + "." + 
                                 joinPoint.getSignature().getName();
        
        logger.info("Processing web request: {}", controllerMethod);
        
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null && !arg.getClass().getName().startsWith("org.springframework")) {
                logger.debug("Request parameter: {}", arg.toString());
            }
        }
    }

    // After advice for controller methods
    @After("controllerLayer()")
    public void logControllerResponse(JoinPoint joinPoint) {
        String controllerMethod = joinPoint.getTarget().getClass().getSimpleName() + "." + 
                                 joinPoint.getSignature().getName();
        
        logger.info("Completed web request: {}", controllerMethod);
    }

    // Pointcut for book borrowing operations
    @Pointcut("execution(* com.library.service.BookService.borrowBook(..)) || " +
              "execution(* com.library.service.BookService.returnBook(..))")
    public void bookTransactions() {}

    // Around advice for book transactions
    @Around("bookTransactions()")
    public Object auditBookTransactions(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("Book transaction started: {} with parameters: {}", 
                   methodName, java.util.Arrays.toString(args));
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            
            long duration = System.currentTimeMillis() - startTime;
            
            if (result instanceof Boolean && (Boolean) result) {
                logger.info("Book transaction successful: {} completed in {} ms", methodName, duration);
            } else {
                logger.warn("Book transaction failed: {} completed in {} ms", methodName, duration);
            }
            
            return result;
            
        } catch (Throwable e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("Book transaction error: {} failed after {} ms with error: {}", 
                        methodName, duration, e.getMessage());
            throw e;
        }
    }

    // Pointcut for book CRUD operations
    @Pointcut("execution(* com.library.service.BookService.addBook(..)) || " +
              "execution(* com.library.service.BookService.updateBook(..)) || " +
              "execution(* com.library.service.BookService.deleteBook(..))")
    public void bookCrudOperations() {}

    // Before advice for book CRUD operations
    @Before("bookCrudOperations()")
    public void validateBookOperations(JoinPoint joinPoint) {
        String operation = joinPoint.getSignature().getName();
        logger.info("Validating book operation: {}", operation);
        
        // Add any validation logic here
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] != null) {
            logger.debug("Operating on: {}", args[0].toString());
        }
    }

    // After returning advice for successful book operations
    @AfterReturning(pointcut = "bookCrudOperations()", returning = "result")
    public void auditSuccessfulBookOperations(JoinPoint joinPoint, Object result) {
        String operation = joinPoint.getSignature().getName();
        logger.info("Book operation completed successfully: {}", operation);
        
        if (result != null) {
            logger.debug("Operation result: {}", result.toString());
        }
    }
}
