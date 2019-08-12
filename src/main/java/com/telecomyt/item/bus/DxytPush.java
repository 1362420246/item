package com.telecomyt.item.bus;


import com.telecomyt.item.entity.TaskGroup;

import java.util.List;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.component
 * @email zhoupengbing@telecomyt.com.cn
 * @description  推送服务
 * @createTime 2019年07月17日 12:43:00
 * @Version v1.0
 */
public interface DxytPush {

    /**
     *  事项通知 ： 发布了任务
     * @param taskGroup 任务信息（包含创建人）
     * @param taskCardIds 任务执行人
     * @param taskCopierIds 任务抄送人
     */
   void publishingTasks(TaskGroup taskGroup ,List<String> taskCardIds ,List<String> taskCopierIds);

    /**
     *  事项通知 ：创建人的行为 ：修改/结束/撤回 任务
     * @param groupId 组id
     *
     */
   void creatorBehavior(Integer groupId ,String pushConstant);

    /**
     *  事项通知 ：执行人的行为 ：开始/完成/放弃 任务
     * @param groupId 组id
     * @param taskCardId 执行人
     *
     */
    void executorBehavior(Integer groupId, String pushConstant , String taskCardId);

}
