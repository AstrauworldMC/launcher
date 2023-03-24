package fr.timeto.astrauworld.launcher.main;

import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.timeto.astrauworld.launcher.pagesutilities.Mod;
import fr.timeto.astrauworld.launcher.pagesutilities.Server;

import java.nio.file.Paths;

import static fr.timeto.astrauworld.launcher.main.Launcher.*;

public class AstrauworldMC extends Server {
    public AstrauworldMC() {
        super(
                "AstrauworldMC",
                false,
                Launcher.mcVersion,
                MODLOADER_FORGE,
                forgeVersion,
                serverOptions,
                true,
                Paths.get("GameFiles"),
                Launcher.optifineVersion);

        setIpOnConnect("207.180.196.61");

        mods.add(new Mod("Backpacked",                  "2.1.10",              new CurseFileInfo(352835, 3923041)));
        mods.add(new Mod("Medieval Craft",              "1.18.2 weapons only", new CurseFileInfo(416811, 3858618)));
        mods.add(new Mod("Epic Knights [...]",          "7.1",                 new CurseFileInfo(509041, 4393487)));
        /* |_> */ mods.add(new Mod("Arichitectury API", "v4.11.89",            new CurseFileInfo(419699, 4384391)));
        mods.add(new Mod("Cosmetic Armor Reworked",     "v2a",                 new CurseFileInfo(237307, 4016730)));
        mods.add(new Mod("Just Enough Items",           "9.7.1.255",           new CurseFileInfo(238222, 3940240)));
        mods.add(new Mod("Simple Voice Chat",           "2.3.28",              new CurseFileInfo(416089, 4372207)));
        mods.add(new Mod("Alex's Mobs",                 "1.18.6",              new CurseFileInfo(426558, 3853078)));
        /* |_> */ mods.add(new Mod("Citadel",           "1.11.3",              new CurseFileInfo(331936, 3783096)));
        mods.add(new Mod("HT's TreeChop",               "0.17.4",              new CurseFileInfo(421377, 4388478)));
        mods.add(new Mod("HT's TreePlant",              "0.3.1",               new CurseFileInfo(433969, 3912070)));
        mods.add(new Mod("Corpse",                      "1.0.1",               new CurseFileInfo(316582, 3694258)));
        mods.add(new Mod("MmmMmmMmmMmm",                "1.5.2",               new CurseFileInfo(225738, 3820503)));
        mods.add(new Mod("Joy of Painting",             "1.0.1",               new CurseFileInfo(350727, 4119502)));
        mods.add(new Mod("Music Maker Mod",             "1.0.2",               new CurseFileInfo(341448, 4131736)));
        mods.add(new Mod("Create",                      "v0.5.0",              new CurseFileInfo(328085, 3864525)));
        /* |_> */ mods.add(new Mod("Flywheel",          "0.6.3",               new CurseFileInfo(486392, 3864518)));
        mods.add(new Mod("Guard Villagers",             "1.4.3",               new CurseFileInfo(360203, 3823106)));
        mods.add(new Mod("InvMove",                     "v0.8.2",              new CurseFileInfo(581854, 4346452)));
        /* |_> */ mods.add(new Mod("Cloth Config API",  "v6.4.90",             new CurseFileInfo(348521, 3972426)));
        mods.add(new Mod("FramedBlocks",                "5.4.0",               new CurseFileInfo(441647, 3850130)));
        mods.add(new Mod("This Rocks!",                 "1.0.4",               new CurseFileInfo(558126, 4341453)));
        mods.add(new Mod("Bed Benefits",                "6.0.2",               new CurseFileInfo(377051, 3807788)));
        /* |_> */ mods.add(new Mod("Bookshelf",         "13.2.52",             new CurseFileInfo(228525, 4351251)));
        mods.add(new Mod("YDM's Weapon Master",         "v3.0.3",              new CurseFileInfo(608235, 3919398)));
        mods.add(new Mod("Tool Belt",                   "1.18.9",              new CurseFileInfo(260262, 4124030)));
        mods.add(new Mod("Human Companions",            "1.7.3",               new CurseFileInfo(570319, 4279118)));
        mods.add(new Mod("Small Ships",                 "2.0.0-Alpha-0.4",     new CurseFileInfo(450659, 3908056)));
        mods.add(new Mod("Storage Drawers",             "10.2.1",              new CurseFileInfo(223852, 3807626)));
        mods.add(new Mod("Jumpy Boats",                 "0.1.0.3",             new CurseFileInfo(542110, 3682173)));
        mods.add(new Mod("SecurityCraft",               "v1.9.3.1",            new CurseFileInfo(64760 , 3921270)));
        mods.add(new Mod("Farmer's Delight",            "1.2",                 new CurseFileInfo(398521, 3999153)));
        mods.add(new Mod("MrCrayfish's Furniture Mod",  "7.0.0-pre35",         new CurseFileInfo(55438 , 4374992)));
        mods.add(new Mod("ParCool!",                    "2.0.0.3-R",           new CurseFileInfo(482378, 3969410)));
        mods.add(new Mod("Aquaculture 2",               "2.3.10",              new CurseFileInfo(60028 , 4183848)));
        mods.add(new Mod("Quark",                       "3.2-358",             new CurseFileInfo(243121, 3840125)));
        /* |_> */ mods.add(new Mod("AutoRegLib",        "1.7-53",              new CurseFileInfo(250363, 3642382)));
        mods.add(new Mod("Calemi's Economy",            "1.0.1",               new CurseFileInfo(579403, 3778254)));
        mods.add(new Mod("Calemi Core",                 "1.0.14",              new CurseFileInfo(573646, 3778251)));
        mods.add(new Mod("Macaw's Bridges",             "v2.0.6",              new CurseFileInfo(351725, 4178158)));
        mods.add(new Mod("Macaw's Furniture",           "v3.0.2",              new CurseFileInfo(359540, 4018178)));
        mods.add(new Mod("Macaw's Roofs",               "v2.2.2",              new CurseFileInfo(352039, 4205653)));
        mods.add(new Mod("Macaw's Doors",               "v1.0.8",              new CurseFileInfo(378646, 4381503)));
        mods.add(new Mod("Macaw's Path and Pavings",    "v1.0.2",              new CurseFileInfo(629153, 4126518)));
        mods.add(new Mod("Macaw's Lights and Lamps",    "v1.0.5",              new CurseFileInfo(502372, 4358260)));
        mods.add(new Mod("Macaw's Fences and Walls",    "v1.0.7",              new CurseFileInfo(453925, 4204541)));
        mods.add(new Mod("Macaw's Trapdoors",           "v1.0.8",              new CurseFileInfo(400933, 4181456)));
        mods.add(new Mod("Macaw's Paintings",           "",                    new CurseFileInfo(438116, 3922996)));
        mods.add(new Mod("Macaw's Windows",             "v2.1.1",              new CurseFileInfo(363569, 4203418)));
        mods.add(new Mod("Rare Ice",                    "v0.4.1",              new CurseFileInfo(373774, 3669561)));
        mods.add(new Mod("Colytra",                     "5.2.0.4",             new CurseFileInfo(280200, 4087911)));
        /* |_> */ mods.add(new Mod("Caelus API",        "3.0.0.2",             new CurseFileInfo(308989, 3650485)));
        mods.add(new Mod("Flash's NPCs",                "1.1.4v2",             new CurseFileInfo(517167, 3760573)));
        mods.add(new Mod("EmoteCraft",                  "2.2.5",               new CurseFileInfo(403422, 3955900)));
        /* |_> */ mods.add(new Mod("PlayerAnimator",    "1.0.2",               new CurseFileInfo(658587, 4418152)));
            /* |_> */ mods.add(new Mod("Bendy-lib",     "2.1.1",               new CurseFileInfo(623373, 3930014)));
    }
}
