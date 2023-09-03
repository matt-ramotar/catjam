package xyz.ramotar.catjam

import android.os.Build.VERSION.SDK_INT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import xyz.ramotar.catjam.models.Category
import xyz.ramotar.catjam.models.CategoryType
import xyz.ramotar.catjam.models.Slackmoji

@Composable
actual fun rememberAsyncImagePainter(url: String): Painter {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(url)
        .build()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    imageLoader.enqueue(imageRequest)

    return coil.compose.rememberAsyncImagePainter(url, imageLoader)
}

private fun getDrawableId(slackmoji: Slackmoji): Int {
    return when (slackmoji) {
        Slackmoji.ZeroZero -> R.drawable.zero_zero
        Slackmoji.Aaaaaa -> R.drawable.aaaaaa
        Slackmoji.Ack -> R.drawable.ack
        Slackmoji.Ahhhhhhhh -> R.drawable.ahhhhhhhhh
        Slackmoji.Alert -> R.drawable.alert
        Slackmoji.AmongUs -> R.drawable.among_us
        Slackmoji.AmongUsOrangeDance -> R.drawable.among_us_orange_dance
        Slackmoji.AmongUsParty -> R.drawable.among_us_party
        Slackmoji.Android -> R.drawable.android
        Slackmoji.AndyDwyerAmazed -> R.drawable.andy_dwyer_amazed
        Slackmoji.ArchLinux -> R.drawable.archlinux
        Slackmoji.BabyYoda -> R.drawable.baby_yoda_soup
        Slackmoji.BananaDance -> R.drawable.bananadance
        Slackmoji.Barbie -> R.drawable.barbie
        Slackmoji.BarbieDance -> R.drawable.barbie_dance
        Slackmoji.BillHaderDancing -> R.drawable.billhader_dancing
        Slackmoji.BirdRun -> R.drawable.bird_run
        Slackmoji.BirthdayPartyParrot -> R.drawable.birthday_party_parrot
        Slackmoji.BlinkingGuy -> R.drawable.blinkingguy
        Slackmoji.BlobAww -> R.drawable.blob_aww
        Slackmoji.BlobClap -> R.drawable.blob_clap
        Slackmoji.BlobCozy -> R.drawable.blob_cozy
        Slackmoji.BlobDance -> R.drawable.blob_dance
        Slackmoji.BlobHelp -> R.drawable.blob_help
        Slackmoji.BlobHighFive -> R.drawable.blob_highfive
        Slackmoji.BlobHype -> R.drawable.blob_hype
        Slackmoji.BlobNo -> R.drawable.blob_no
        Slackmoji.BlobSalute -> R.drawable.blob_salute
        Slackmoji.BlobWave -> R.drawable.blob_wave
        Slackmoji.BlobYes -> R.drawable.blob_yes
        Slackmoji.BlowKiss -> R.drawable.blow_kiss
        Slackmoji.BlueLightSaber -> R.drawable.bluelightsaber
        Slackmoji.BongoBlob -> R.drawable.bongo_blob
        Slackmoji.BouncingElephant -> R.drawable.bouncing_elephant
        Slackmoji.Bread -> R.drawable.bread
        Slackmoji.BugcatSmash -> R.drawable.bugcat_smash
        Slackmoji.BugcatYes -> R.drawable.bugcat_yes
        Slackmoji.CandyHeartLove -> R.drawable.candy_heart_love
        Slackmoji.CatConfused -> R.drawable.cat_confused
        Slackmoji.CatRoomba -> R.drawable.cat_roomba
        Slackmoji.CatRoombaExceptionallyFast -> R.drawable.cat_roomba_exceptionally_fast
        Slackmoji.CatRoombaHyperFast -> R.drawable.cat_roomba_hyper_fast
        Slackmoji.Catjam -> R.drawable.catjam
        Slackmoji.ChatGpt -> R.drawable.chat_gpt
        Slackmoji.Checked -> R.drawable.checked
        Slackmoji.Cheer -> R.drawable.cheer
        Slackmoji.ChicagoFlag -> R.drawable.chicago_flag
        Slackmoji.ChillSquirtle -> R.drawable.chill_squirtle
        Slackmoji.ChristmasParrot -> R.drawable.christmas_parrot
        Slackmoji.ClappingInclusive -> R.drawable.clapping_inclusive
        Slackmoji.CoffinDance -> R.drawable.coffin_dance
        Slackmoji.Coin -> R.drawable.coin
        Slackmoji.ComputerRage -> R.drawable.computerrage
        Slackmoji.ConfusedDog -> R.drawable.confused_dog
        Slackmoji.ConfusedNumbers -> R.drawable.confused_numbers
        Slackmoji.CongaParrot -> R.drawable.conga_parrot
        Slackmoji.CoolDoge -> R.drawable.cool_doge
        Slackmoji.CowboyCoolCryMildPanic -> R.drawable.cowboy_cool_cry_mild_panic
        Slackmoji.CowboyEyes -> R.drawable.cowboy_eyes
        Slackmoji.CryCat -> R.drawable.crycat
        Slackmoji.CryingSunglasses -> R.drawable.crying_sunglasses
        Slackmoji.CryingSunglassesCowboy -> R.drawable.crying_sunglasses_cowboy
        Slackmoji.Dankies -> R.drawable.dankies
        Slackmoji.Database -> R.drawable.database
        Slackmoji.DeployParrot -> R.drawable.deployparrot
        Slackmoji.Done -> R.drawable.done
        Slackmoji.DumpsterFire -> R.drawable.dumpster_fire
        Slackmoji.ElmoFire -> R.drawable.elmofire
        Slackmoji.EnglandParrot -> R.drawable.england_parrot
        Slackmoji.EverythingsFineParrot -> R.drawable.everythings_fine_parrot
        Slackmoji.ExcuseMe -> R.drawable.excuseme
        Slackmoji.ExtremeTeamwork -> R.drawable.extreme_teamwork
        Slackmoji.Eyes -> R.drawable.eyes
        Slackmoji.Facepalm -> R.drawable.facepalm
        Slackmoji.FastMeowParty -> R.drawable.fast_meow_party
        Slackmoji.Fifty -> R.drawable.fifty
        Slackmoji.FistBump -> R.drawable.fistbump
        Slackmoji.FourHundredFour -> R.drawable.four_hundred_four
        Slackmoji.FrogWowScroll -> R.drawable.frog_wow_scroll
        Slackmoji.FrogWowWhat -> R.drawable.frog_wow_what
        Slackmoji.Fry -> R.drawable.fry
        Slackmoji.FryTakeMyMoney -> R.drawable.fry_take_my_money
        Slackmoji.Godzilla -> R.drawable.godzilla
        Slackmoji.Google -> R.drawable.google
        Slackmoji.GremlinOhNo -> R.drawable.gremlin_ohno
        Slackmoji.Hacker -> R.drawable.hacker
        Slackmoji.Hackerman -> R.drawable.hackerman
        Slackmoji.HeadbangingParrot -> R.drawable.headbanging_parrot
        Slackmoji.HomerDisappear -> R.drawable.homer_disappear
        Slackmoji.HotCoffee -> R.drawable.hot_coffee
        Slackmoji.Huhh -> R.drawable.huhh
        Slackmoji.HyperFastParrot -> R.drawable.hyperfastparrot
        Slackmoji.ImSy -> R.drawable.im_shy
        Slackmoji.InfiniteKermit -> R.drawable.infinite_kermit
        Slackmoji.Instagram -> R.drawable.instagram
        Slackmoji.Jul -> R.drawable.jul
        Slackmoji.KenSunglasses -> R.drawable.ken_sunglasses
        Slackmoji.KittySpin -> R.drawable.kitty_spin
        Slackmoji.KittyThumbsUp -> R.drawable.kitty_thumbsup
        Slackmoji.Kubernetes -> R.drawable.kubernetes
        Slackmoji.LaptopParrot -> R.drawable.laptop_parrot
        Slackmoji.Lawvocado -> R.drawable.lawvocado
        Slackmoji.LeoToast -> R.drawable.leo_toast
        Slackmoji.LeslieKnopeThumbsUp -> R.drawable.leslie_knope_thumbs_up
        Slackmoji.LetMeIn -> R.drawable.let_me_in
        Slackmoji.LolRip -> R.drawable.lol_rip
        Slackmoji.LolSob -> R.drawable.lolsob
        Slackmoji.Mancity -> R.drawable.mancity
        Slackmoji.MarioLuigiDance -> R.drawable.mario_luigi_dance
        Slackmoji.MeowAdorable -> R.drawable.meow_adorable
        Slackmoji.MeowAttention -> R.drawable.meow_attention
        Slackmoji.MeowBirthday -> R.drawable.meow_birthday
        Slackmoji.MeowBongoTap -> R.drawable.meow_bongotap
        Slackmoji.MeowCode -> R.drawable.meow_code
        Slackmoji.MeowCoffee -> R.drawable.meow_coffee
        Slackmoji.MeowCoffeeSpitting -> R.drawable.meow_coffeespitting
        Slackmoji.MeowHeart -> R.drawable.meow_heart
        Slackmoji.MeowHeartBongo -> R.drawable.meow_heart_bongo
        Slackmoji.MeowKnife -> R.drawable.meow_knife
        Slackmoji.MeowNoddies -> R.drawable.meow_noddies
        Slackmoji.MeowParty -> R.drawable.meow_party
        Slackmoji.MeowPopcorn -> R.drawable.meow_popcorn
        Slackmoji.MeowThumbsUp -> R.drawable.meow_thumbsup
        Slackmoji.Meowdy -> R.drawable.meowdy
        Slackmoji.MfDoom -> R.drawable.mf_doom
        Slackmoji.MildPanicIntensifies -> R.drawable.mild_panic_intensifies
        Slackmoji.Mindblown -> R.drawable.mindblown
        Slackmoji.Money -> R.drawable.money
        Slackmoji.Mop -> R.drawable.mop
        Slackmoji.Nike -> R.drawable.nike
        Slackmoji.NinetyNine -> R.drawable.ninety_nine
        Slackmoji.NoProblem -> R.drawable.no_problem
        Slackmoji.NoWayCandyHeart -> R.drawable.noway_candyheart
        Slackmoji.OldManYellsAtCloud -> R.drawable.old_man_yells_at_cloud
        Slackmoji.OldManYellsAtSalesforce -> R.drawable.old_man_yells_at_salesforce
        Slackmoji.OmgLol -> R.drawable.omglol
        Slackmoji.OneThousand -> R.drawable.one_thousand
        Slackmoji.Otter -> R.drawable.otter
        Slackmoji.PartyBlob -> R.drawable.party_blob
        Slackmoji.PartyParrot -> R.drawable.partyparrot
        Slackmoji.PatrickPanic -> R.drawable.patrick_panic
        Slackmoji.PepeBye -> R.drawable.pepe_bye
        Slackmoji.PepeWhy -> R.drawable.pepe_why
        Slackmoji.Pikachu -> R.drawable.pikachu
        Slackmoji.PikachuSurprisedLoop -> R.drawable.pikachu_surprised_loop
        Slackmoji.PinchedFingers -> R.drawable.pinched_fingers
        Slackmoji.Pirate -> R.drawable.pirate
        Slackmoji.PirateShip -> R.drawable.pirate_ship
        Slackmoji.Pokeball -> R.drawable.pokeball
        Slackmoji.PugDanceFast -> R.drawable.pug_dance_fast
        Slackmoji.QuadParrot -> R.drawable.quad_parrot
        Slackmoji.Question -> R.drawable.question
        Slackmoji.Rage -> R.drawable.rage
        Slackmoji.SadCowboy -> R.drawable.sad_cowboy
        Slackmoji.SadBlob -> R.drawable.sadblob
        Slackmoji.ShakingFistDarkMode -> R.drawable.shaking_fist_dark_mode
        Slackmoji.Siren -> R.drawable.siren
        Slackmoji.Sisyphus -> R.drawable.sisyphus
        Slackmoji.SixtyFpsParrot -> R.drawable.sixty_fps_parrot
        Slackmoji.Slack -> R.drawable.slack
        Slackmoji.Slayy -> R.drawable.slayy
        Slackmoji.Slayyed -> R.drawable.slayyyed
        Slackmoji.Smart -> R.drawable.smart
        Slackmoji.Sonic -> R.drawable.sonic
        Slackmoji.SpaceFloat -> R.drawable.space_float
        Slackmoji.Spotify -> R.drawable.spotify
        Slackmoji.Stonks -> R.drawable.stonks
        Slackmoji.Success -> R.drawable.success
        Slackmoji.SurprisedPikachu -> R.drawable.surprised_pikachu
        Slackmoji.TadaGoth -> R.drawable.tada_goth
        Slackmoji.TakeMyMoney -> R.drawable.take_my_money
        Slackmoji.TenTen -> R.drawable.ten_ten
        Slackmoji.ThankYou -> R.drawable.thank_you
        Slackmoji.Thanks -> R.drawable.thanks
        Slackmoji.ThankYouParty -> R.drawable.thankyou
        Slackmoji.TheMoreYouKnow -> R.drawable.the_more_you_know
        Slackmoji.ThinkAboutIt -> R.drawable.think_about_it
        Slackmoji.ThisIsFineFire -> R.drawable.this_is_fine_fire
        Slackmoji.Troll -> R.drawable.troll
        Slackmoji.TrumpMugshot -> R.drawable.trump_mugshot
        Slackmoji.TryNotToCry -> R.drawable.try_not_to_cry
        Slackmoji.TumbleWeed -> R.drawable.tumbleweed
        Slackmoji.TypingCat -> R.drawable.typingcat
        Slackmoji.UnoReverse -> R.drawable.uno_reverse
        Slackmoji.UnsettlingGrin -> R.drawable.unsettling_grin
        Slackmoji.VibeRabbit -> R.drawable.vibe_rabbit
        Slackmoji.WaveHello -> R.drawable.wave_hello
        Slackmoji.YesChef -> R.drawable.yes_chef
        Slackmoji.Youtube -> R.drawable.youtube
    }
}

@Composable
actual fun rememberImagePainter(slackmoji: Slackmoji): Painter {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(getDrawableId(slackmoji))
        .build()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    return coil.compose.rememberAsyncImagePainter(imageRequest, imageLoader = imageLoader)
}

@Composable
internal actual fun rememberImagePainter(category: Category): Painter {
    val painter = when (category.type) {
        CategoryType.Native -> {
            val resId = when (category.id) {
                "people" -> R.drawable.laugh_regular
                "nature" -> R.drawable.dog_solid
                "foods" -> R.drawable.hamburger_solid
                "activity" -> R.drawable.skiing_solid
                "places" -> R.drawable.map_solid
                "objects" -> R.drawable.lightbulb_regular
                "symbols" -> R.drawable.icons_solid
                "flags" -> R.drawable.flag_solid
                else -> R.drawable.laugh_regular
            }
            painterResource(resId)
        }

        CategoryType.Slackmoji -> {
            rememberImagePainter(Slackmoji.Catjam)
        }

        CategoryType.Custom -> {
            painterResource(R.drawable.palette_solid)
        }
    }

    return remember { painter }
}