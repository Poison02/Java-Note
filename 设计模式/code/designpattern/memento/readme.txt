备忘录模式
形状编辑器和复杂的撤销/恢复功能
该图像编辑器允许修改屏幕上形状的颜色和位置。 但任何修改都可被撤销和重复。

“撤销” 功能基于备忘录和命令模式的合作。 编辑器记录命令的执行历史。 在执行任何命令之前， 它都会生成备份并将其连接到一个命令对象。 而在执行完成后， 它会将已执行的命令放入历史记录中。

当用户请求撤销操作时， 编辑器将从历史记录中获取最近的命令， 恢复在该命令内部保存的状态备份。 如果用户再次请求撤销操作， 编辑器将恢复历史记录中的下一个命令， 以此类推。

被撤销的命令都将保存在历史记录中， 直至用户对屏幕上的形状进行了修改。 这对恢复被撤销的命令来说至关重要。