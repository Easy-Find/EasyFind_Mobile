package com.easy.myapplication.shared.ProductItem

import DestinationTarget
import LatandLong
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.easy.myapplication.R
import com.easy.myapplication.dto.Estabelecimento
import com.easy.myapplication.screens.Mapa.GetRouteCallback
import com.easy.myapplication.shared.StarRatingBar.StarRatingBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.conversorTime
import com.easy.myapplication.utils.formatTime
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import kotlin.time.ExperimentalTime


data class Time(
    val car: Double?,
    val bike: Double?,
    val people: Double?
)

data class DataProductItem(
    val id:Long=0L,
    val name: String,
    val imagens: String? = "",
    val qtdStars: Double,
    val shop: String,
    val price: Double,
    val time: Time?,
    val latitude: Double?,
    val longitude: Double?,
    val estabelecimento:Estabelecimento?
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
    fun ProductItem(data: DataProductItem, getRouteCallback: GetRouteCallback,navigate:(Long)->Unit) {


        Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp).clickable {
            MainScope().launch {
                navigate(data.id)
            }
        }



    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .width(75.dp)
                .height(85.dp)
        ) {
            AsyncImage(
                model =  data.imagens,
                contentDescription = "Imagem do produto",
                error = painterResource(R.mipmap.fone),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
        }


        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(modifier = Modifier.fillMaxWidth(0.7f)) {
                    Title(content = data.name, overflow = TextOverflow.Ellipsis,maxLines=1)
                }

                StarRatingBar(rating = data.qtdStars.toFloat(), size = 5F)
            }
            Row {
                Subtitle(content = data.shop, color = Primary)
            }
            Row {
                Subtitle(content = "R$" + data.price.toString(), color = Primary)
            }


            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(id = R.mipmap.car),
                                        contentDescription = "Icone",
                                        modifier = Modifier.width(10.dp)
                                    )
                                }
                                Column {
                                    Subtitle(
                                        content = data.time?.car?.let { conversorTime(it) },
                                    )
                                }
                            }
                        }
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(id = R.mipmap.people),
                                        contentDescription = "Icone",
                                        modifier = Modifier.width(10.dp)
                                    )
                                }
                                Column {
                                    Subtitle(
                                        content = data.time?.people?.let { conversorTime(it) }
                                    )
                                }
                            }
                        }
                    }
                }
                Column( horizontalAlignment = Alignment.End) {
                    IconButton(modifier = Modifier.background(Primary).border(1.dp,
                        Color.Transparent, RoundedCornerShape(100)).width(20.dp).height(20.dp).padding(5.dp) ,onClick = {
                        if (data.latitude != null && data.longitude != null) {
                            getRouteCallback.getRoute(destination = DestinationTarget(coordinates = LatandLong(data.latitude,data.longitude),estabelecimento = data.estabelecimento))
                        }
                    }){
                        Image( painter = painterResource(id = R.mipmap.tracker), contentDescription ="Descrição", modifier = Modifier.width(30.dp).height(30.dp) )
                    }

                }
            }
        }
    }
}