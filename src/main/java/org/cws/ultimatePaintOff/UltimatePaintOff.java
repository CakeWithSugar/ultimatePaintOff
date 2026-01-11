package org.cws.ultimatePaintOff;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.akonda.Akonda;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.akonda.AkondaExtend;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.nova.Nova;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.nova.NovaExtend;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.nova.NovaPuls;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.snap.Snap;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.snap.SnapComplex;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.snap.SnapLight;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.triatler.TriAtler;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.triatler.TriAtlerExtend;
import org.cws.ultimatePaintOff.arsenal.primaryWeapons.triatler.TriAtlerPegasus;
import org.cws.ultimatePaintOff.arsenal.ultimateWeapons.*;
import org.cws.ultimatePaintOff.executors.*;
import org.cws.ultimatePaintOff.listeners.*;
import org.cws.ultimatePaintOff.listsAndInventories.ArsenalInventory;
import org.cws.ultimatePaintOff.listsAndInventories.InGameMenu;
import org.cws.ultimatePaintOff.listsAndInventories.VoteInventory;
import org.cws.ultimatePaintOff.managers.*;

import java.util.Objects;

public final class UltimatePaintOff extends JavaPlugin {
    public static UltimatePaintOff instance;
    public BasicValues basicValues;

    public SnowballManager snowballManager;
    public ItemManager itemManager;
    public MessageManager messageManager;
    public QueueManager queueManager;
    public PaintManager paintManager;
    public SelectionManager selectionManager;
    public GameManager gameManager;
    public InventoryManager inventoryManager;
    public ArenaManager arenaManager;
    public StopManager stopManager;
    public PointsManager pointsManager;
    public DamageManager damageManager;
    public ScoreManager scoreManager;
    public ArsenalCoordinatingManager arsenalCoordination;

    public ArsenalInventory arsenalInventory;
    public InGameMenu inGameMenu;
    public VoteInventory voteInventory;

    public Snap snap;
    public SnapLight snapLight;
    public Nova nova;
    public NovaPuls novaPuls;
    public NovaExtend novaExtend;
    public TriAtler triAtler;
    public TriAtlerExtend triAtlerExtend;
    public TriAtlerPegasus triAtlerPegasus;
    public SnapComplex snapComplex;
    public Akonda akonda;
    public AkondaExtend akondaExtend;

    public Helixpulser helixpulser;
    public PlatzRegen platzRegen;
    public Sonnenschutz sonnenschutz;
    public JettBlaster jettBlaster;
    public FokusBooster fokusBooster;
    public KrawumKreisel krawumKreisel;

    @Override
    public void onEnable() {
        instance = this;
        registerManager();
        registerEvents();
        registerCommand();
        registerListsAndInventories();
        registerUlts();
        registerWeapons();
        setUp();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(instance);
    }

    private void setUp() {
        queueManager.setup();
        paintManager.setup();
        gameManager.setup();
        arenaManager.setup();
        pointsManager.setup();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerAttemptPickupItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
    }

    private void registerManager() {
        this.itemManager = new ItemManager();
        this.snowballManager = new SnowballManager();
        this.messageManager = new MessageManager();
        this.queueManager = new QueueManager();
        this.paintManager = new PaintManager();
        this.selectionManager = new SelectionManager();
        this.gameManager = new GameManager();
        this.inventoryManager = new InventoryManager();
        this.arenaManager = new ArenaManager();
        this.stopManager = new StopManager();
        this.pointsManager = new PointsManager();
        this.damageManager = new DamageManager();
        this.arsenalCoordination = new ArsenalCoordinatingManager();
        this.scoreManager = new ScoreManager();
    }

    private void registerListsAndInventories() {
        this.arsenalInventory = new ArsenalInventory();
        this.voteInventory = new VoteInventory();
        this.basicValues = new BasicValues();
        this.inGameMenu = new InGameMenu();
    }

    private void registerWeapons() {
        this.snap = new Snap();
        this.snapLight = new SnapLight();
        this.nova = new Nova();
        this.novaPuls = new NovaPuls();
        this.novaExtend = new NovaExtend();
        this.triAtler = new TriAtler();
        this.triAtlerExtend = new TriAtlerExtend();
        this.triAtlerPegasus = new TriAtlerPegasus();
        this.snapComplex = new SnapComplex();
        this.akonda = new Akonda();
        this.akondaExtend = new AkondaExtend();
    }

    private void registerUlts() {
        this.helixpulser = new Helixpulser();
        this.platzRegen = new PlatzRegen();
        this.sonnenschutz = new Sonnenschutz();
        this.jettBlaster = new JettBlaster();
        this.fokusBooster = new FokusBooster();
        this.krawumKreisel = new KrawumKreisel();
    }

    private void registerCommand() {
        Objects.requireNonNull(getCommand("poarsenal")).setExecutor(new ArsenalExecutor());
        Objects.requireNonNull(getCommand("poqueue")).setExecutor(new QueueExecutor());
        Objects.requireNonNull(getCommand("poleave")).setExecutor(new LeaveExecutor());
        Objects.requireNonNull(getCommand("postart")).setExecutor(new StartExecutor());
        Objects.requireNonNull(getCommand("poinfo")).setExecutor(new InfoExecutor());
        Objects.requireNonNull(getCommand("poarena")).setExecutor(new ArenaExecutor());
        Objects.requireNonNull(getCommand("postop")).setExecutor(new StopExecutor());
    }
    public static UltimatePaintOff getInstance() {
        return instance;
    }
}
