package de.crash.netty.packets

import de.crash.netty.packets.handshake.HandshakeHandler
import de.crash.netty.packets.login.DisconnectLoginPacket
import de.crash.netty.packets.login.LoginStartHandler
import de.crash.netty.packets.login.LoginSuccessPacket
import de.crash.netty.packets.play.DisconnectPlayPacket
import de.crash.netty.packets.play.JoinGamePacket
import de.crash.netty.packets.status.PingStatusHandler
import de.crash.netty.packets.status.PongStatusPacket
import de.crash.netty.packets.status.RequestHandler
import de.crash.netty.packets.status.ResponsePacket
import java.util.*

enum class PacketType(val id: Int) {
    // HANDSHAKE

    HANDSHAKE(0),

    // STATUS

    RESPONSE(0),
    REQUEST(0),
    PING_STATUS(1),
    PONG_STATUS(1),

    // LOGIN
    //Clientbound

    DISCONNECT_LOGIN(0),
    ENCRYPTION_REQUEST(1),
    LOGIN_SUCCESS(2),
    SET_COMPRESSION(3),
    LOGIN_PLUGIN_REQUEST(4),

    //Serverbound

    LOGIN_START(0),
    ENCRYPTION_RESPONSE(1),
    LOGIN_PLUGIN_RESPONSE(2),

    // PLAY
    //Clientbound

    SPAWN_ENTITY(0),
    SPAWN_EXPERIENCE(1),
    SPAWN_LIVING_ENTITY(2),
    SPAWN_PAINTING(3),
    SPAWN_PLAYER(4),
    SCULK_VIBRATION_SIGNAL(5),
    ENTITY_ANIMATION(6),
    STATISTICS(7),
    ACKNOWLEDGE_PLAYER_DIGGING(8),
    BLOCK_BREAK_ANIMATION(9),
    BLOCK_ENTITY_DATA(10),
    BLOCK_ACTION(11),
    BLOCK_CHANGE(12),
    BOSSBAR(13),
    DIFFICULTY(14),
    CHAT_MESSAGE(15),
    CLEAR_TITLES(16),
    TAB_COMPLETE(17),
    DECLARE_COMMANDS(18),
    CLOSE_WINDOW(19),
    WINDOW_ITEMS(20),
    WINDOW_PROPERTY(21),
    SET_SLOT(22),
    SET_COOLDOWN(23),
    PLUGIN_MESSAGE_CLIENTBOUND(24),
    NAMED_SOUND_EFFECT(25),
    DISCONNECT_PLAY(26),
    ENTITY_STATUS(27),
    EXPLOSION(28),
    UNLOAD_CHUNK(29),
    CHANGE_GAME_STATE(30),
    OPEN_HORSE_WINDOW(31),
    INITIALIZE_WORLD_BORDER(32),
    KEEP_ALIVE_CLIENTBOUND(33),
    CHUNK_DATA(34),
    EFFECT(35),
    PARTICLE(36),
    UPDATE_LIGHT(37),
    JOIN_GAME(38),
    MAP_DATA(39),
    TRADE_LIST(40),
    ENTITY_POSITION(41),
    ENTITY_POSITION_ROTATION(42),
    ENTITY_ROTATION(43),
    VEHICLE_MOVE(44),
    OPEN_BOOK(45),
    OPEN_WINDOW(46),
    OPEN_SIGN_EDITOR(47),
    PING_PLAY(48),
    CRAFT_RECIPE_RESPONSE(49),
    PLAYER_ABILITIES_CLIENTBOUND(50),
    END_COMBAT_EVENT(51),
    ENTER_COMBAT_EVENT(52),
    DEATH_COMBAT_EVENT(53),
    PLAYER_INFO(54),
    FACE_PLAYER(55),
    PLAYER_POSITION_AND_LOOK_CLIENTBOUND(56),
    UNLOCK_RECIPES(57),
    DESTROY_ENTITY(58),
    REMOVE_ENTITY_EFFECT(59),
    RESOURCE_PACK_SEND(60),
    RESPAWN(61),
    ENTITY_HEAD_LOOK(62),
    MULTI_BLOCK_CHANGE(63),
    SELECT_ADVANCEMENT_TAB(64),
    ACTION_BAR(65),
    WORLD_BORDER_CENTER(66),
    WORLD_BORDER_LERP_SIZE(67),
    WORLD_BORDER_SIZE(68),
    WORLD_BORDER_WARNING_DELAY(69),
    WORLD_BORDER_WARNING_REACH(70),
    CAMERA(71),
    HELD_ITEM_CHANGE_CLIENTBOUND(72),
    UPDATE_VIEW_POSITION(73),
    UPDATE_VIEW_DISTANCE(74),
    SPAWN_POSITION(75),
    DISPLAY_SCOREBOARD(76),
    ENTITY_METADATA(77),
    ATTACH_ENTITY(78),
    ENTITY_VELOCITY(79),
    ENTITY_EQUIPMENT(80),
    SET_EXPERIENCE(81),
    UPDATE_HEALTH(82),
    SCOREBOARD_OBJECTIVE(83),
    SET_PASSENGERS(84),
    TEAMS(85),
    UPDATE_SCORE(86),
    SET_TITLE_SUBTITLE(87),
    TIME_UPDATE(88),
    SET_TITLE_TEXT(89),
    SET_TITLE_TIMES(90),
    ENTITY_SOUND_EFFECT(91),
    SOUND_EFFECT(92),
    STOP_SOUND(93),
    PLAYER_LIST_HEADER_AND_FOOTER(94),
    NBT_QUERY_RESPONSE(95),
    COLLECT_ITEM(96),
    ENTITY_TELEPORT(97),
    ADVANCEMENTS(98),
    ENTITY_PROPERTIES(99),
    ENTITY_EFFECT(100),
    DECLARE_RECIPES(101),
    TAGS(102),

    //Serverbound

    TELEPORT_CONFIRM(0),
    QUERY_BLOCK_NBT(1),
    SET_DIFFICULTY(2),
    CHAT_MESSAGE_SERVERBOUND(3),
    CLIENT_STATUS(4),
    CLIENT_SETTINGS(5),
    TAB_COMPLETE_SERVERBOUND(6),
    CLICK_WINDOW_BUTTON(7),
    CLICK_WINDOW(8),
    CLOSE_WINDOW_SERVERBOUND(9),
    PLUGIN_MESSAGE_SERVERBOUND(10),
    EDIT_BOOK(11),
    QUERY_ENTITY_NBT(12),
    INTERACT_ENTITY(13),
    GENERATE_STRUCTURE(14),
    KEEP_ALIVE(15),
    LOCK_DIFFICULTY(16),
    PLAYER_POSITION(17),
    PLAYER_POSITION_ROTATION_SERVERBOUND(18),
    PLAYER_ROTATION(19),
    PLAYER_MOVEMENT(20),
    VEHICLE_MOVE_SERVERBOUND(21),
    STEER_BOAT(22),
    PICK_ITEM(23),
    CRAFT_RECIPE_REQUEST(24),
    PLAYER_ABILITIES_SERVERBOUND(25),
    PLAYER_DIGGING(26),
    ENTITY_ACTION(27),
    STEER_VEHICLE(28),
    PONG_PLAY(29),
    SET_RECIPE_BOOK_STATE(30),
    SET_DISPLAYED_RECIPE(31),
    NAME_ITEM(32),
    RESOURCE_PACK_STATUS(33),
    ADVANCEMENT_TAB(34),
    SELECT_TRADE(35),
    SET_BEACON_EFFECT(36),
    HELD_ITEM_CHANGE_SERVERBOUND(37),
    UPDATE_COMMAND_BLOCK(38),
    UPDATE_COMMAND_BLOCK_MINECART(39),
    CREATIVE_INVENTORY_ACTION(40),
    UPDATE_JIGSAW_BLOCK(41),
    UPDATE_STRUCTURE_BLOCK(42),
    UPDATE_SIGN(43),
    ANIMATION_SERVERBOUND(44),
    SPECTATE(45),
    PLAYER_BLOCK_PLACEMENT(46),
    USE_ITEM(47)
}