package com.seven.gwc.core.aop;


import com.seven.gwc.config.constant.ConfigConsts;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.base.BaseEntity;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.entity.PositionEntity;
import com.seven.gwc.modular.system.service.PositionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据过滤处理
 */
@Aspect
@Component
@DependsOn("springContextUtil")
public class DataScopeAspect {
    @Autowired
    private PositionService positionService;
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    // 配置织入点
    @Pointcut("@annotation(com.seven.gwc.core.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null) {
            return;
        }
        // 获取当前的用户
        ShiroUser currentUser = ShiroKit.getUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.getId().equals(ConfigConsts.ADMIN_ID)) {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user      用户
     * @param deptAlias 别名
     * @param userAlias 别名
     */
    public void dataScopeFilter(JoinPoint joinPoint, ShiroUser user, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();

        for (String positionId : user.getPositionList()) {
            PositionEntity positionEntity = positionService.getById(positionId);
            String dataScope = positionEntity.getDataScope();

            if (DATA_SCOPE_ALL.equals(dataScope)) {
                //全部数据
                sqlString = new StringBuilder();
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                //自定义数据
                sqlString.append(ToolUtil.format(" OR {}.id IN ( SELECT dept_id FROM sys_position_dept WHERE position_id IN ( '{}' ) ) ", deptAlias, positionEntity.getId()));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                //部门数据
                sqlString.append(ToolUtil.format(" OR {}.id = {}", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                //部门及以下数据
                sqlString.append(ToolUtil.format(" OR {}.id IN (SELECT id FROM sys_dept WHERE id = {} OR pids LIKE '%[ {} ]%')",
                        deptAlias, user.getDeptId(), user.getDeptId()));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                //仅本人数据
                if (ToolUtil.isNotEmpty(userAlias)) {
                    sqlString.append(ToolUtil.format(" OR {}.id = '{}' ", userAlias, user.getId()));
                } else {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(" OR 1=0 ");
                }
            }
        }

        if (ToolUtil.isNotEmpty(sqlString.toString())) {
            BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ") ");
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

    public static void main(String[] args) {
        String aa = "123456789";
        System.out.println(aa.substring(4));

    }
}
