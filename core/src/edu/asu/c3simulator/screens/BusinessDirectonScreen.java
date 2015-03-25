package edu.asu.c3simulator.screens;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.asu.c3simulator.simulation.SimulationScreen;
import edu.asu.c3simulator.widgets.NavigationPanel;

/**
 * Displays two options for general company goals and highlights selected option.
 * 
 * @author Alyahya, Mohammed some of Zack's code was reused here
 */
public class BusinessDirectonScreen implements SimulationScreen
{
	private Game game;
	private Stage stage;
	private Skin skin;
	private ButtonGroup optionCheckBoxesGroup;
	private CheckBox longTermOption, shortTermOption;
	
	public BusinessDirectonScreen(Game game)
	{
		this.game = game;
		this.stage = new Stage();
		this.skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		Table directions = new Table();
		
		Actor longTermDirection = createLongTermDirection();
		Actor shortTermDirection = createShortTermDirection();
		
		longTermOption = new CheckBox(" Long Term", skin);
		shortTermOption = new CheckBox(" Short Term", skin);
		
		Label longTermLabel = new Label("Long Term", skin);
		Label shortTermLabel = new Label("Short Term", skin);
		
		optionCheckBoxesGroup = new ButtonGroup(longTermOption, shortTermOption);
		optionCheckBoxesGroup.setMaxCheckCount(1);
		optionCheckBoxesGroup.setMinCheckCount(0);
		optionCheckBoxesGroup.setUncheckLast(true);
		
		directions.add(longTermLabel).top();
		directions.add(shortTermLabel).top();
		directions.row();
		directions.add(longTermDirection).top();
		directions.add(shortTermDirection).top();
		directions.row();
		directions.add(longTermOption);
		directions.add(shortTermOption);
		
		directions.setTransform(true);
		
		directions.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
		
		TextButton continueButton = new TextButton("Continue >", skin);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				// TODO: Transition to Funding Screen
				game.setScreen(new FundingScreen(game));
			}
		});
		
		continueButton.setPosition(stage.getWidth() - continueButton.getPrefWidth()
				- (0.01f * stage.getWidth()), (0.01f * stage.getWidth()));
		
		// TODO add Corner Advisor
		// TODO add Home Button
		
		NavigationPanel navigationPanel = createNavigationPanel();
		
		stage.addActor(navigationPanel);
		stage.addActor(continueButton);
		stage.addActor(directions);
	}
	
	/**
	 * Resets the selection and makes sure both checkboxes are unchecked.
	 */
	public void resetSelection()
	{
		longTermOption.setChecked(false);
		shortTermOption.setChecked(false);
	}
	
	@Override
	public NavigationPanel createNavigationPanel()
	{
		// TODO add screens
		NavigationPanel navigationPanel = new NavigationPanel(game, skin);
		navigationPanel.addButton("Industry", null);
		navigationPanel.addButton("Direction", null);
		navigationPanel.addButton("Funding", null);
		navigationPanel.addButton("Tasks", null);
		
		navigationPanel.setPosition(0.01f * stage.getWidth(), stage.getHeight()
				- (0.3f * stage.getHeight()));
		
		return navigationPanel;
	}
	
	/**
	 * This method creates the long term labels and button that will be displayed in the
	 * screen.
	 * 
	 * @return the GUI that was created.
	 */
	private Actor createLongTermDirection()
	{
		String communityOutreach[] = { "Community Outreach:",
				"Building company's name recognition ",
				"within the community through outreach ", "projects." };
		String revenue[] = { "Revenue:", "Concrete, annual fiscal goals supporting ",
				"revenue expectations." };
		String customerService[] = { "Customer Service:",
				"Maintain a long-lasting bond and ", "build a devoted following." };
		
		VerticalGroup longTermDirectionText = new VerticalGroup();
		longTermDirectionText.addActor(createParagraph(communityOutreach));
		longTermDirectionText.addActor(createParagraph(revenue));
		longTermDirectionText.addActor(createParagraph(customerService));
		
		longTermDirectionText.space(25);
		longTermDirectionText.left();
		return longTermDirectionText;
	}
	
	/**
	 * This method creates the short term labels and button that will be displayed in the
	 * screen.
	 * 
	 * @return the GUI that was created.
	 */
	private Actor createShortTermDirection()
	{
		String communityOutreach[] = { "Community Outreach:",
				"Incentives for employees who volunteer",
				"with designated community projects." };
		String revenue[] = { "Revenue:", "One month contracts for advising",
				"consultants" };
		String customerService[] = { "Customer Service:",
				"Host monthly drawings for free", "products or discounts on future ",
				"purchases." };
		
		VerticalGroup shortTermDirectionText = new VerticalGroup();
		shortTermDirectionText.addActor(createParagraph(communityOutreach));
		shortTermDirectionText.addActor(createParagraph(revenue));
		shortTermDirectionText.addActor(createParagraph(customerService));
		
		shortTermDirectionText.space(25);
		shortTermDirectionText.left();
		return shortTermDirectionText;
	}
	
	/**
	 * A method that creates the paragraph that will be displayed and organize it in a
	 * table.
	 * 
	 * @param text
	 *            a string array of the text that will create the table.
	 * @return The table created holding the text labels.
	 */
	private Table createParagraph(String[] text)
	{
		// REFACTOR: Combine with #createDifficultyChoiceDescriptionHard
		Table requestedParagraph = new Table();
		
		List<Label> lines = new LinkedList<>();
		// REFACTOR: Load lines from file
		for (String stringLine : text)
		{
			lines.add(new Label(stringLine, skin));
		}
		
		for (Label line : lines)
		{
			line.setAlignment(Align.left);
			requestedParagraph.add(line).left();
			requestedParagraph.row();
		}
		return requestedParagraph;
	}
	
	/**
	 * @return The Business Direction that was chosen by the user. "none" will be returned
	 *         if no option was chosen.
	 */
	public String getChosenOption()
	{
		if (longTermOption.isChecked())
			return "long term";
		else if (shortTermOption.isChecked())
			return "short term";
		else
			return "none";
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height);
	}
	
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(stage);
		
	}
	
	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
		
	}
	
	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("The method is not implemented yet.");
	}
	
	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("The method is not implemented yet.");
	}
	
	@Override
	public void dispose()
	{
		stage.dispose();
		this.game = null;
	}
}
