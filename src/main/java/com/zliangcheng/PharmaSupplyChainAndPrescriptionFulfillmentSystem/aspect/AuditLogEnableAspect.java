package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PrescriptionResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.AuditLog;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogEnableAspect {
    private final AuditLogRepository auditLogRepository;

    @Around("@annotation(auditLogEnable)")
    public Object auditLog(ProceedingJoinPoint joinPoint, AuditLogEnable auditLogEnable) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 记录基本信息
        String action = auditLogEnable.action();
        String methodName = method.getName();
        String className = joinPoint.getTarget().getClass().getName();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        String paramJson = new ObjectMapper().writeValueAsString(args);

        // 执行目标方法
        PrescriptionResponse result = (PrescriptionResponse) joinPoint.proceed();
        String resultJson = new ObjectMapper().writeValueAsString(result);

        // 记录返回值
        AuditLog auditLog = new AuditLog();
        auditLog.setAttemptTime(LocalDateTime.now());
        auditLog.setPatientId(Strings.isBlank(result.getPatientId()) ? null : Long.valueOf(result.getPatientId()));
        auditLog.setPharmacyId(Strings.isBlank(result.getPatientId()) ? null : Long.valueOf(result.getPharmacyId()));
        auditLog.setPrescriptionId(Strings.isBlank(result.getPrescriptionId()) ? null : Long.valueOf(result.getPrescriptionId()));
        auditLog.setDrugsRequested(result.getDrugIds().keySet().stream().map(String::valueOf).toList());
        auditLog.setDrugsDispensed(result.getDrugIds());
        auditLog.setFailureReason(result.getErrorMessage());
        auditLog.setStatus(result.getStatus());

        auditLogRepository.save(auditLog);


        // 这里可以添加保存日志的逻辑
        System.out.printf("执行方法: %s.%s, Action: %s, 参数: %s, 返回值: %s%n",
                className, methodName, action, paramJson, resultJson);

        return result;
    }
}
