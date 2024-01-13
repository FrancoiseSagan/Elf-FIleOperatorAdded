### Elf的大创版本

增加了文件操作，可以生成压缩后的bin文件以及通过bin文件解压为csv浮点数文件

仅修改了main函数，增加了一个包，并把test中的分块类移入这个包

主分支是初始版本，修改在另一个分支中完成

##### 问题：

1、最后不到1000个浮点数不能被压缩(已解决，现在使用arraylist，能处理空行和最后部分)

2、目前块数使用1 byte，也就是最多255（已解决，现在为2bytes）

3、后边有的部分被我删了(但是我忘了)，还没push回来

##### 注：

本仓库为本人不想在实验室的库里拉分支而建立的，源代码请移步：

<a href="https://github.com/Spatio-Temporal-Lab/elf">Elf的库</a>

##### 修改日志

12.24 文件操作类改为非static，增加构造函数

12.25 filereader 改为arraylist，现在可以读取最后一个块

12.26 块的数量改为2bytes
