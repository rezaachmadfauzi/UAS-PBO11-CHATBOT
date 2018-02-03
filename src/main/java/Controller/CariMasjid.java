package Controller;

import Interface.interCariMasjid;
import LineSDK.Message;
import Model.Result;
import Model.mCariMasjid;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CariMasjid {
    public void getDataMasjid(interCariMasjid callback){
        ServiceAPI client=new ClientAPI().createService(ServiceAPI.class);
        HashMap<String,String> params=new HashMap<>();
        params.put("lat","-6.888519");
        params.put("lng","107.618493");
        Call<mCariMasjid> call=client.getDataMasjid(params);
        call.enqueue(new Callback<mCariMasjid>() {
            @Override
            public void onResponse(Call<mCariMasjid> call, Response<mCariMasjid> response) {
                List<Result> data=response.body().getResult();
                callback.onSuccess(data);

            }

            @Override
            public void onFailure(Call<mCariMasjid> call, Throwable t) {

            }
        });
    }
    public void replyToUser(String rToken,String lChannelAccessToken, List<Result> value){
        String imageUrl="https://islamify.id/imagebot/okemas.png";
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(imageUrl, value.get(0).getNama().substring(0,40), "Masjid Ini Deket Banget Bro", Arrays.asList(
                                new PostbackAction("Tuju Masjid",
                                        "#")
                        )),
                        new CarouselColumn(imageUrl, value.get(1).getNama().substring(0,40), "Masjid Ini Deket Banget Bro", Arrays.asList(
                                new PostbackAction("Tuju Masjid",
                                        "#")
                        )),
                        new CarouselColumn(imageUrl, value.get(2).getNama().substring(0,40), "Masjid Ini Deket Banget Bro", Arrays.asList(
                                new PostbackAction("Tuju Masjid",
                                        "#")
                        )),
                        new CarouselColumn(imageUrl, value.get(3).getNama().substring(0,40), "Masjid Ini Deket Banget Bro", Arrays.asList(
                                new PostbackAction("Tuju Masjid",
                                        "#")
                        )),
                        new CarouselColumn(imageUrl, value.get(4).getNama().substring(0,40), "Masjid Ini Deket Banget Bro", Arrays.asList(
                                new PostbackAction("Tuju Masjid",
                                        "#")
                        ))
                ));
        TemplateMessage templateMessage = new TemplateMessage("Masjid Terdekat", carouselTemplate);
        TextMessage textMessage =new TextMessage("Halo kak, ini aku nemu masjid terdekat dari kakak");
        List<com.linecorp.bot.model.message.Message> message=new ArrayList<>();
        message.add(textMessage);
        message.add(templateMessage);
        ReplyMessage replyMessage = new ReplyMessage(rToken,message);

        try {
            Response<BotApiResponse> response = LineMessagingServiceBuilder
                    .create(lChannelAccessToken)
                    .build()
                    .replyMessage(replyMessage)
                    .execute();
            System.out.println("Reply Message: " + response.code() + " " + response.message());
        } catch (IOException e) {
            System.out.println("Exception is raised ");
            e.printStackTrace();
        }

    }
}
