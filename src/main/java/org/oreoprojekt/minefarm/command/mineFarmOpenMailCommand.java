package org.oreoprojekt.minefarm.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Listener.mineFarmMailEventListener;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

import java.io.IOException;


public class mineFarmOpenMailCommand implements CommandExecutor {
    private mineFarmMailSendSystem mineFarmMailSendSystem;
    private Minefarm plugin;
    public mineFarmOpenMailCommand(Minefarm plugin) {
        this.plugin = plugin;
        this.mineFarmMailSendSystem = new mineFarmMailSendSystem(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {  //명령어 사용자가 플레이어인 경우
            //이벤트 객체 생성
            mineFarmMailEventListener.OpenMailBox event = new mineFarmMailEventListener.OpenMailBox((Player)sender);
            //플러그인 관리자에 이벤트 호출 요청
            Bukkit.getPluginManager().callEvent(event);
            return true;    //true값을 반환하면 명령어가 성공한 것으로 간주
        }
        else if(sender instanceof ConsoleCommandSender) {   //명령어 사용자가 콘솔인 경우
            sender.sendMessage("콘솔에서는 이 명령어를 실행할 수 없습니다.");
            return false;   //false값을 반환하면 명령어가 실패한 것으로 간주
        }
        return false;   //false값을 반환하면 명령어가 실패한 것으로 간주
    }
}
