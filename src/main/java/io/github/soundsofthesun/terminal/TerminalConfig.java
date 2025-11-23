package io.github.soundsofthesun.terminal;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "terminal")
@Config(name = "terminal-cfg", wrapperName = "TerminalCfg")
public class TerminalConfig {
    public int max_network_size = 1024;
}
