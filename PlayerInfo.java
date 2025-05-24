package player;

import java.util.UUID;

public class PlayerInfo
{

private String name;
private String id;

public PlayerInfo(String name){
        this.name=name;
        this.id=UUID.randomUUID().toString();
        }

public String getName(){
        return name;
        }

public String getId(){
        return id;
        }

}