龙珠消消乐--逻辑层实现
=============================================

Author: JZS

Email : jzs12@software.nju.edu.cn

Date  : 14/05/22


仅用于学习交流，请勿作商业用途。

Do Not Used This Program For Further Commercial Purpose.

基于软工三游戏规则要求的三消逻辑层实现，带自动运行脚本供测试。


>已实现的功能：
>+ 9*9的游戏格子；
>+ 不得出现死局，也就是全无可消除的选择；
>+ 只能在垂直角度对游戏类图标进行交换； 
>+ 水平或垂直连续 3个图标直接消除；
>+ 水平或垂直连续 4个图标消除后形成道具A；
>+ 水平或垂直连续 5个图标消除后形成道具B；
>+ 水平和垂直两组均为 3个图标的消除后形成道具 A；
>+ 水平和垂直两组超过 3个图标的消除后形成道具 B；
>+ 道具 A，游戏中产生，是某一种图标的升级版本，A可以独立消除也同色消除。        
>  当 A形成消除后，将形成周边爆炸效果，消除周边 8个图标；
>+ 道具 B，游戏中产生，不是任何一种基础图标的升级版本，只能独立消除，
>  当独立消除的时候，将其垂直路线上消除；
>+ 得分规则
>  三个消除得100分，四个消除得200分，五个消除的500分。
>  横竖两列：三三得200分，三四得500分。（若有掉落形成四四得500）
>  道具A效果主动触发得分200；消除触发，先进行消除计分，再加上200。
>  道具B使用得分800。
