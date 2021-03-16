# trading_stock-in-stock-market
to simulate socket-based client-server system to trade a single stock in a simple stock market, Java, Socket, Client Server

socket-based client-server system trade a single stock in a simple stock market

rules:
1) There is only a single stock in a simple stock market.
2) At any point in time, exactly one trader owns the single stock (connect in Java).
This trader with the stock needs to decide whom to give the stock to (themselves is ok).
3) New traders can join the market at any time, if new trader join, notice others.
Every trader joining the market has to be assigned a unique ID.
4) At any point in time, any trader can leave the market (disconenct in Java).
If the tarder does not own the stock, just notice others his/her leaving.
If the trader with the stock leaves, the server will give the stock to one of the remaining traders.
If no remaining traders in the market, the server store stocks and the first trader to connect will receive the stock.

规则：
1） 只有一只股票
2） 任何时候，交易者（tarder）都可以进入市场，也即连接服务器。
（第一个进入的交易者会被服务器分配这只股票：stock）
拥有股票的交易者决定，将这只股票给谁，可以是其他的任何交易者，也可以是自己。
3） 任何时候，新的交易者都可以进入市场，有新进入的交易者，通知其他的在场的交易者。
每一个交易者都有一个用于识别的ID。
4） 任何时候，任何在场的交易者都可以退出市场。
如果要退出的交易者不拥有这只股票，通知其他在场的交易者，然后离场。
如果要退出的交易者拥有这只股票，则是服务器接管这个股票并分配给其余在场的任何一个交易者。
如果没有其他交易者在场，服务器则保存这只股票，直到有交易者入场，并将其给第一个入场的。

using:
Java, sockets, two distince threads, namely, 2 Java projects here.

大概的演示：
1. 开启服务器
2. 开启客户端，陆陆续续开启了一共6个客户端
3. 开启客户端，首先第一个输入是 join，告诉服务器入场了，服务器会记录
4. 第一个入场的，服务器会将stock分配给该trader
5. 交易的时候，输入 transact (id)， 这个地方的id即是即将收到stock的id
6. 第一批，陆续开启5个客户端，各有不同的id，然后互相转股（transact）
7. 然后5个客户端，逐个离场，如果是持有stock的离场，会将stock转给server服务器，并由server服务器转给剩下的任意一个在场的trader
8. 如果是不持有的trader离场，仅仅通知其他在场的trader更新数据即可
9. 当最后一个trader离场，stock自然就转给了server，由server暂时持有
10. 第一批的trader都离场，第二批新来的第1个trader入场，server将stock转给该trader
11. 任何trader，如果离场就只用输出leave即可，如果是想彻底关闭客户端，是紧接着在leave输入之后，（服务器收到之后），再输入close，就关闭了该socket，即关闭了程序
