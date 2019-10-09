package com.liubike.customokhttp.okhttp.chain;

import com.liubike.customokhttp.okhttp.Request2;
import com.liubike.customokhttp.okhttp.Response2;
import com.liubike.customokhttp.okhttp.SocketRequestServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 连接服务器的拦截器
 *
 * @author Heiko
 * @date 2019/10/8
 */
public class ConnectionServerInterceptor implements Interceptor2 {

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        SocketRequestServer srs = new SocketRequestServer();

        Request2 request = chain2.getRequest();
        Socket socket = new Socket(srs.getHost(request), srs.getPort(request));

        // 请求
        // output
        OutputStream os = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        String requestAll = srs.getRequestHeaderAll(request);
        // Log.d(TAG, "requestAll:" + requestAll);
        System.out.println("requestAll:" + requestAll);
        bufferedWriter.write(requestAll); // 给服务器发送请求 --- 请求头信息 所有的
        bufferedWriter.flush(); // 真正的写出去...

        // 响应

        /*Response2 response2 = new Response2();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            String readLine = null;
            if ((readLine = br.readLine()) != null) {
                System.out.println("响应的数据：" + readLine);
            } else {
                break;
            }
        }*/

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Response2 response2 = new Response2();

        // 取出 响应码
        String readLine = bufferedReader.readLine(); // 读取第一行 响应头信息
        // 服务器响应的:HTTP/1.1 200 OK
        String[] strings = readLine.split(" ");
        response2.setCode(Integer.parseInt(strings[1]));

        // 取出响应体，只要是空行下面的，就是响应体
        String readerLine = null;
        try {
            while ((readerLine = bufferedReader.readLine()) != null) {
                System.out.println("读到数据了:"+readerLine);
                if ("".equals(readerLine)) {
                    // 读到空行了，就代表下面就是 响应体了
                    response2.setBody(bufferedReader.readLine());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response2;
    }
}
