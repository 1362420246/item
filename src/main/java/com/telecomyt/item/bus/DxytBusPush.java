package com.telecomyt.item.bus;

import com.telecomyt.item.bus.data.Creator;
import com.telecomyt.item.bus.data.SendNoticeRequestMessageData;
import com.telecomyt.item.constant.PushConstant;
import com.telecomyt.item.dto.TaskIdState;
import com.telecomyt.item.entity.CacheUser;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.utils.OperationUtils;
import com.telecomyt.item.web.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.component
 * @email zhoupengbing@telecomyt.com.cn
 * @description  总线推送方式
 * @createTime 2019年07月17日 12:56:00
 * @Version v1.0
 */
public class DxytBusPush implements DxytPush {

    @Autowired
    private BusPushUtils  bushPushUtils;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private DxytBusPushConfig dxytBusPushConfig;

    /**
     * 事项通知 ： 发布了任务
     * @param taskGroup 任务信息（包含创建人）
     * @param taskCardIds 任务执行人
     * @param taskCopierIds 任务抄送人
     */
    @Async
    @Override
    public void publishingTasks(TaskGroup taskGroup , List<String> taskCardIds , List<String> taskCopierIds) {
        CacheUser from = OperationUtils.getCacheUserrByCardId(taskGroup.getCreatorCardId());
        if(taskCopierIds != null && taskCopierIds.size() >0){
            taskCardIds.addAll(taskCopierIds);
        }
        Creator creator = null;
        String notification = null ;
        if(from != null){
            creator = Creator.builder().nickName(from.getName()).username(from.getUid()).build();
            notification = String.format(PushConstant.PUBLISHING_TASK , from.getName()) ;
        }
        SendNoticeRequestMessageData message = new SendNoticeRequestMessageData(taskGroup,creator,notification ,dxytBusPushConfig.getURL(),2);
        bushPushUtils.sendNoticeByUsername(from , taskCardIds , message);
    }

    /**
     *  事项通知 ：创建人的行为 ：修改/结束/撤回 任务
     * @param groupId 组id
     *
     */
    @Async
    @Override
    public void creatorBehavior(Integer groupId ,String pushConstant ) {
        TaskGroup taskGroup = taskMapper.getTaskGroupByGroupId(groupId);
        CacheUser from = OperationUtils.getCacheUserrByCardId(taskGroup.getCreatorCardId());
        //查询执行者
        List<TaskIdState> taskCardIds = taskMapper.queryTaskCardId(taskGroup.getGroupId());
        //查询抄送者
        List<TaskIdState> taskCopierIds = taskMapper.queryTaskCopierId(taskGroup.getGroupId());
        List<String> cardIds = taskCardIds.stream().map(TaskIdState::getCardId).collect(Collectors.toList());
        List<String> copierIds = taskCopierIds.stream().map(TaskIdState::getCardId).collect(Collectors.toList());
        if(taskCopierIds.size() > 0){
            cardIds.addAll(copierIds);
        }
        Creator creator = null;
        String notification = null ;
        if(from != null){
            creator = Creator.builder().nickName(from.getName()).username(from.getUid()).build();
            notification = String.format(pushConstant, from.getName()) ;
        }
        SendNoticeRequestMessageData message = new SendNoticeRequestMessageData(taskGroup ,creator ,notification ,dxytBusPushConfig.getURL(),2);
        bushPushUtils.sendNoticeByUsername(from , cardIds , message);
    }

    /**
     *  事项通知 ：执行人的行为 ：开始/完成/放弃 任务
     * @param groupId 组id
     * @param taskCardId 执行人id
     *
     */
    @Async
    @Override
    public void executorBehavior(Integer groupId, String pushConstant , String taskCardId) {
        TaskGroup taskGroup = taskMapper.getTaskGroupByGroupId(groupId);
        CacheUser from = OperationUtils.getCacheUserrByCardId(taskGroup.getCreatorCardId());
        CacheUser taskUser = OperationUtils.getCacheUserrByCardId(taskCardId);
        //查询抄送者
        List<TaskIdState> taskCopierIds = taskMapper.queryTaskCopierId(taskGroup.getGroupId());
        List<String> copierIds = taskCopierIds.stream().map(TaskIdState::getCardId).collect(Collectors.toList());
        copierIds.add(taskGroup.getCreatorCardId());
        Creator creator = null;
        String notification = null ;
        if(from != null){
            creator = Creator.builder().nickName(from.getName()).username(from.getUid()).build();
            notification = String.format(pushConstant , taskUser.getName()) ;
        }
        SendNoticeRequestMessageData message = new SendNoticeRequestMessageData(taskGroup ,creator ,notification ,dxytBusPushConfig.getURL(),1);
        bushPushUtils.sendNoticeByUsername(from , copierIds , message);
    }


}
