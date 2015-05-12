package tools;

import org.redisson.Redisson;
import org.redisson.core.MessageListener;
import org.redisson.core.RTopic;

import com.xrtb.commands.BasicCommand;
import com.xrtb.commands.Echo;

public class Commands {
	RTopic<BasicCommand> commands;
	Redisson redisson;
 public static void main(String [] args) {
	 Commands c = new Commands();
	 c.sendEcho();
 }
 
 public Commands() {
	 Redisson redisson = Redisson.create();
     commands = redisson.getTopic("commands");
     
     RTopic<BasicCommand> responses = redisson.getTopic("responses");
    responses.addListener(new MessageListener<BasicCommand>() {
         @Override
         public void onMessage(BasicCommand msg) {
             System.out.println("<<<<<" + msg);
         }
     });
 }
 
 public void sendEcho() {
	 Echo e = new Echo();
	 System.out.println(">>>>> " + e);
	 commands.publish(e);
 }

}
