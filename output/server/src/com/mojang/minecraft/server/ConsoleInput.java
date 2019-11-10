package com.mojang.minecraft.server;

import com.mojang.minecraft.server.MinecraftServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

final class ConsoleInput extends Thread {

   // $FF: synthetic field
   private MinecraftServer server;


   ConsoleInput(MinecraftServer var1) {
      this.server = var1;
      super();
   }

   public final void run() {
      try {
         BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
         String var2 = null;

         while((var2 = var1.readLine()) != null) {
            synchronized(MinecraftServer.a(this.server)) {
               MinecraftServer.a(this.server).add(var2);
            }
         }

         MinecraftServer.logger.warning("stdin: end of file! No more direct console input is possible.");
      } catch (IOException var5) {
         MinecraftServer.logger.warning("stdin: ioexception! No more direct console input is possible.");
         var5.printStackTrace();
      }
   }
}
