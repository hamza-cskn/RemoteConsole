package mc.obliviate.remoteconsole.bukkit;

import mc.obliviate.remoteconsole.server.RemoteConsoleServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class RemoteConsoleBukkit extends JavaPlugin {

	@Override
	public void onEnable() {
		Bukkit.getLogger().info("Loading process started.");
		int port = 25500;
		RemoteConsoleServer server = new RemoteConsoleServer(input -> {
			Bukkit.getScheduler().runTask(this, () -> {
				try {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), input.readUTF());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				;
			});

		});
		server.start(port);
		Bukkit.getLogger().info("Server started on: " + port);
		Bukkit.getLogger().info("Process successfully completed.");
	}


}
