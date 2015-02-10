package edu.asu.c3simulator.testing;

import java.text.DateFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.asu.c3simulator.simulation.C3Simulation;
import edu.asu.c3simulator.simulation.Industry;
import edu.asu.c3simulator.simulation.Player;
import edu.asu.c3simulator.simulation.SocioeconomicStatus;
import edu.asu.c3simulator.util.Observer;
import edu.asu.c3simulator.widgets.CornerAdvisor;
import edu.asu.c3simulator.widgets.IndustrySelector;
import edu.asu.c3simulator.widgets.Padding;
import edu.asu.c3simulator.widgets.PlayerStatusDisplay;
import edu.asu.c3simulator.widgets.TextAreaX;
import edu.asu.c3simulator.widgets.WidgetFactory;

/**
 * Development screen to play with and test new features and graphic elements before using
 * them in production code.
 * 
 * This class should not be deployed.
 * 
 * @author Moore, Zachary
 * 
 */
public class Sandbox implements Screen
{
	/**
	 * Width and Height at which this screen was designed. Can be used in resize
	 * operations.
	 */
	private static final int DESIGN_WIDTH = 1280;
	private static final int DESIGN_HEIGHT = 720;
	
	/** Handles all components of this screen (rendering, updating, resizing, etc) */
	private Stage stage;
	
	/** Specifies textures to use for default widgets such as Buttons and Labels */
	@SuppressWarnings("unused")
	private Skin skin;
	
	/**
	 * @param game
	 */
	public Sandbox()
	{
		Viewport stageViewport = new StretchViewport(DESIGN_WIDTH, DESIGN_HEIGHT);
		this.stage = new Stage(stageViewport);
		this.skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		// sandboxTestAreaX();
		// sandboxCornerAdvisor();
		// sandboxPlayerStatusDisplay();
		sandboxIndustrySelector();
	}
	
	/**
	 * Contains a bulleted list component on the screen, with multiple bullet items.
	 * Manufactures the list using
	 * {@link WidgetFactory#createBulletedList(Texture, String...)}
	 */
	private void sandboxBulletedList()
	{
		String texturePath = "images/bullet_white.png";
		Texture bulletTexture = new Texture(texturePath);
		Actor list = WidgetFactory.createBulletedList(bulletTexture, "Item 1", "Item 2",
				"Item 3");
		
		list.setPosition(500, 500);
		stage.addActor(list);
	}
	
	/**
	 * Contains a single IndustrySelector instance using an Industry stub as the content
	 */
	private void sandboxIndustrySelector()
	{
		class IndustryStub implements Industry
		{
			String name;
			
			public IndustryStub(String name)
			{
				this.name = name;
			}
			
			@Override
			public String getName()
			{
				return this.name;
			}
			
			@Override
			public String[] getDescription()
			{
				return new String[] { item(" 1"), item(" 2"), item(" 3") };
			}
			
			private String item(String suffix)
			{
				return name + suffix;
			}
			
		}
		
		IndustrySelector selector = new IndustrySelector();
		selector.add(new IndustryStub("Industry A"));
		selector.add(new IndustryStub("Industry B"));
		selector.add(new IndustryStub("Industry C"));
		selector.add(new IndustryStub("Industry D"));
		selector.add(new IndustryStub("Industry E"));
		
		selector.setPosition(200, 200);
		selector.setSize(600, 200);
		
		stage.addActor(selector);
	}
	
	/**
	 * Contains a single PlayerStatusDisplay drawn in the top left corner
	 */
	private void sandboxPlayerStatusDisplay()
	{
		PlayerStatusDisplay display = new PlayerStatusDisplay(new Player() {
			
			@Override
			public void registerObserver(Observer<Player> observer)
			{
				
			}
			
			@Override
			public SocioeconomicStatus getStatus()
			{
				return SocioeconomicStatus.TEST_STATUS;
			}
			
			@Override
			public C3Simulation getSimulation()
			{
				return new C3Simulation() {
					@Override
					public String getSimulationDateString()
					{
						return "5 Jan, Year TEST";
					}
					
					@Override
					public String getSimulationDateString(String format)
					{
						// TODO Auto-generated method stub return null;
						throw new UnsupportedOperationException(
								"The method is not implemented yet.");
					}
					
					@Override
					public String getSimulationDateString(DateFormat format)
					{
						// TODO Auto-generated method stub return null;
						throw new UnsupportedOperationException(
								"The method is not implemented yet.");
					}
				};
			}
			
			@Override
			public int getNetWorth()
			{
				return 1000000;
			}
			
			@Override
			public int getCapital()
			{
				return 100000;
			}
			
			@Override
			public String getID()
			{
				return "test";
			}
		});
		
		display.left().bottom();
		float displayX = display.getX() + 30;
		float displayY = DESIGN_HEIGHT - display.getPrefHeight() - 20;
		display.setPosition(displayX, displayY);
		stage.addActor(display);
	}
	
	/**
	 * Contains a CornerAdvisor object with placeholder text spanning multiple lines.
	 */
	private void sandboxCornerAdvisor()
	{
		String text = "This is a test of TextAreaX. This is intended to cover multiple lines at a width of 200px. This is the second extention";
		CornerAdvisor advisor = new CornerAdvisor(text);
		
		advisor.debug();
		advisor.setPosition(500, 500);
		
		stage.addActor(advisor);
	}
	
	/**
	 * Contains a TextAreaX object with placeholder text spanning multiple lines.
	 */
	private void sandboxTextAreaX()
	{
		String text = "This is a test of TextAreaX. This is intended to cover multiple lines at a width of 200px. This is the second extention";
		TextAreaX textArea = new TextAreaX(text, 200, 0.5f);
		textArea.setBackground("images/text-bubble-white.png");
		textArea.setBorderPadding(new Padding(20, 50, 15, 10));
		
		Table debug = new Table();
		debug.setPosition(500, 500);
		debug.add(textArea);
		debug.debug();
		
		TextAreaX textArea2 = new TextAreaX(text, 400);
		textArea2.setBackground("images/text-bubble-white.png");
		textArea2.setBorderPadding(new Padding(20, 50, 15, 10));
		textArea2.setPosition(800, 500);
		
		stage.addActor(debug);
		stage.addActor(textArea2);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		stage.dispose();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause()
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		stage.act(delta);
		stage.draw();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(stage);
	}
	
}