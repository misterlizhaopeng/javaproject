package nio_test.nio_socket.socket_communication;

import lombok.Data;

/**
 * @ClassName nio_test.nio_socket.socket_communication.UserInfo
 * @Deacription : 测试用户实体类
 * @Author LP
 * @Date 2021/8/3
 * @Version 1.0
 **/
@Data
public class UserInfo {
    private Long id;
    private String name;
    private String password;
    public UserInfo(){ }
    public UserInfo(Long id,String name,String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
}

