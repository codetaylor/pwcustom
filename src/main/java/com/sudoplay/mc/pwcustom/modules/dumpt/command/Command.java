package com.sudoplay.mc.pwcustom.modules.dumpt.command;

import com.codetaylor.mc.athenaeum.util.FileHelper;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile.IExportProfile;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

public class Command
    extends CommandBase {

  private final Path dataPath;
  private final Logger logger;
  private final Map<String, IExportProfile> exportProfileMap;

  public Command(
      Path dataPath,
      Logger logger,
      Map<String, IExportProfile> exportProfileMap
  ) {

    this.dataPath = dataPath;
    this.logger = logger;
    this.exportProfileMap = exportProfileMap;
  }

  @Override
  public String getName() {

    return "dumpt";
  }

  @Override
  public String getUsage(ICommandSender sender) {

    Set<String> strings = this.exportProfileMap.keySet();
    String values = String.join("|", strings.toArray(new String[strings.size()]));
    return "dumpt " + values;
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

    if (args.length > 0) {

      IExportProfile profile = this.exportProfileMap.get(args[0]);

      if (profile != null) {
        BufferedWriter bufferedWriter = null;

        try {
          Path path = Paths.get(this.dataPath.toString(), profile.getFilename());
          Files.createDirectories(this.dataPath);

          if (Files.exists(path)) {
            Files.delete(path);
          }

          bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
          bufferedWriter.write(String.join(",", profile.getHeaders()) + System.lineSeparator());
          int elementCount = profile.write(bufferedWriter);

          sender.sendMessage(new TextComponentString(elementCount + " elements written to " + profile.getFilename()));

        } catch (Throwable t) {
          this.logger.error("", t);

        } finally {
          FileHelper.closeSilently(bufferedWriter);
        }
      }

    }
  }

}
