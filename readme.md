### Elf的大创版本

增加了文件操作，可以生成压缩后的bin文件以及通过bin文件解压为csv浮点数文件

仅修改了main函数，增加了一个包，并把test中的分块类移入这个包

##### 问题：

1、最后不到1000个浮点数不能被压缩

2、目前块数使用1 byte，也就是最多255（因为数据集都不大，暂时没有修改）

##### 注：

本仓库为本人不想在实验室的库里拉分支而建立的，源代码请移步：

<a href="[Spatio-Temporal-Lab/elf (github.com)](https://github.com/Spatio-Temporal-Lab/elf)">Elf的库</a>

