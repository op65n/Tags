# Tags
A plugin which introduces Chat Tags in a consumer friendly way.
<br>
Version: `0.0.5-Alpha` <br>
Native API: `Paper-1.16.5-R0.1-SNAPSHOT` <br>
Source Code: <a href="https://github.com/op65n/Tags">github.com/op65n/Tags</a> <br>
Developer: `Frcsty` <br>

## How To (Server Owner)
This is a plugin built on PaperAPI, and is required to properly run this plugin.

<b>Installation:</b> <br>
| Place SpigotTags-VERSION.jar (`SpigotTags-0.0.5-Alpha.jar`) file into the plugins folder. <br>
| Start the server, plugin will generate `SpigotTags` directory with files:
* `config.yml`
* `hikari-settings.yml`
* `tags-menu.yml` <br>

| Stop the server after everything has been loaded. <br>
| Open and configure the plugin to your needs. <br>
| Start the server and enjoy the plugin!

## Commands
The plugin provides a few commands <br>

- `/tags` (permission: `/`) <br>
  This command opens up the tags menu which displays the users accessible tags <br>
  <br>

- `/tags reload` (permission: `tags.command.reload`) <br>
  This command requests the config (tag registry) from the database,
  and updates its cache - This update is fired when the plugin first loads
   <br>
  

- `/tags load` (permission: `tags.command.load`) <br>
  This command loads the config (tag registry) to the database - This update is 
  fired when the plugin first loads

## Features
- Hex Support <br>
The plugin offers hex support using various different formats:
  ```
  <#FFFFFF>
  {#FFFFFF}
  &#FFFFFF
  #FFFFFF
  ```

- Gradient Support <br>
The plugin offers gradient support alongside hex:
  ```
  <g:hex1:hex2>Some Text
  ```