package edu.asu.c3simulator.widgets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Creates a button that in turn creates a popup window when clicked. The popup
 * window requires confirmation via button click before selling the company.
 * 
 * @author Reigel, Justin
 *
 */
public class SellCompanyButton extends Table
{

	private TextButton sellButton;
	private Window window;

	public SellCompanyButton(Game game, Skin skin)
	{
		super();
		TextButton continueButton = new TextButton("Continue", skin);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				window.remove();
				Window soldWindow = new Window("Company was sold", skin);

				TextButton confirmationButton = new TextButton("Ok", skin);
				confirmationButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y)
					{
						soldWindow.remove();
					}
				});

				soldWindow.add("").expand().row();
				soldWindow.add(confirmationButton).bottom()
						.height(soldWindow.getPadTop());
				soldWindow.setPosition(200f, 200f);
				soldWindow.setSize(400f, 100f);
				soldWindow.setColor(1f, 1f, 1f, 1f);
				soldWindow.setResizable(false);
				getStage().addActor(soldWindow);
				// TODO: Delete actual business When this SellCompany Button is
				// clicked
			}
		});

		TextButton cancelButton = new TextButton("Cancel", skin);
		cancelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (window != null)
				{
					window.remove();
				}
			}
		});
		sellButton = new TextButton("Sell", skin);
		add(sellButton);
		// TODO: change positioning to the appropriate position for its
		// destination screen
		this.setPosition(1100, 500);
		sellButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				window = new Window("Warning", skin);
				window.defaults().spaceBottom(10);
				window.row().fill().expandX();
				window.add(
						"This will permanently sell your business\n and you will not be able to "
								+ "get it back.\n Are you sure you would like to continue? ")
						.row();
				Table windowButtons = new Table();
				windowButtons.add(continueButton).height(window.getPadTop());
				windowButtons.add(cancelButton).height(window.getPadTop());
				window.add(windowButtons);
				window.getButtonTable().right().bottom();
				window.setPosition(200f, 200f);
				window.setSize(550f, 200f);
				window.setColor(1f, 1f, 1f, 1f);
				window.setResizable(false);
				getStage().addActor(window);
			}
		});

	}
}
