package edu.asu.c3simulator.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.asu.c3simulator.util.Product;
import edu.asu.c3simulator.widgets.NavigationPanel;

/**
 * Display a list of all products created by the business and give the user the ability to
 * either market one of them or the company as a whole. The user is also able to choose
 * from different types of marketing campaigns. This is the first step of two-step wizard.
 * 
 * @author Alyahya, Mohammed
 */
public class MarketingScreen implements Screen
{
	
	private class productIconListener extends ClickListener
	{
		private Label associatedLabel;
		private Product associatedProduct;
		
		/**
		 * @param associatedLabel
		 *            A label which will change colors when the icon is clicked.
		 * @param associatedProduct
		 *            The product that is represented by the object that is being listened
		 *            to.
		 */
		public productIconListener(Label associatedLabel, Product associatedProduct)
		{
			this.associatedLabel = associatedLabel;
			this.associatedProduct = associatedProduct;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			companyPromotionButton.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			associatedLabel.setColor(0.0f, 1.0f, 0.0f, 1.0f);
			if (selectedProductLabel != null && selectedProductLabel != associatedLabel)
				selectedProductLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			selectedProduct = associatedProduct;
			selectedProductLabel = associatedLabel;
			productChosen = true;
		}
	}
	
	private class marketingIconListener extends ClickListener
	{
		private Label associatedLabel;
		private String marketingType;
		
		/**
		 * @param associatedLabel
		 *            A label which will change colors when the icon is clicked
		 * @param marketingType
		 *            The marketing type that the icon is representing.
		 */
		public marketingIconListener(Label associatedLabel, String marketingType)
		{
			this.associatedLabel = associatedLabel;
			this.marketingType = marketingType;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			associatedLabel.setColor(0.0f, 0.0f, 1.0f, 1.0f);
			if (selectedMarketingLabel != null
					&& selectedMarketingLabel != associatedLabel)
				selectedMarketingLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			selectedMarketingMethod = marketingType;
			selectedMarketingLabel = associatedLabel;
			marketingMethodChosen = true;
		}
	}
	
	private Game game;
	private Stage stage;
	private Skin skin;
	private Product selectedProduct;
	private Label selectedProductLabel, selectedMarketingLabel;
	private TextButton companyPromotionButton;
	private String selectedMarketingMethod = "";
	private boolean promoteCompany = false, productChosen = false,
			marketingMethodChosen = false;
	
	public MarketingScreen(Game game)
	{
		this.game = game;
		this.stage = new Stage();
		this.skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		// TODO add Corner Advisor
		// TODO add Home Button
		
		NavigationPanel navigationPanel = createNavigationPanel();
		
		Table mainTable = createMainTable();
		mainTable.setPosition(
				stage.getWidth() - 0.01f * stage.getWidth() - mainTable.getWidth(),
				0.01f * stage.getHeight());
		
		stage.addActor(navigationPanel);
		stage.addActor(mainTable);
	}
	
	/**
	 * The method creates the navigation panel.
	 * 
	 * @return the NavigationPanel actor that was created.
	 */
	private NavigationPanel createNavigationPanel()
	{
		NavigationPanel navigationPanel = new NavigationPanel(game, skin);
		
		// TODO add screens
		navigationPanel.addButton("Products", null);
		navigationPanel.addSubButton("Products", "Pre-Market", null);
		navigationPanel.addSubButton("Products", "Current", null);
		navigationPanel.addSubButton("Products", "Retired", null);
		navigationPanel.addButton("Product Growth", null);
		navigationPanel.addSubButton("Product Growth", "Supply", null);
		navigationPanel.addSubButton("Product Growth", "Demand", null);
		navigationPanel.addButton("Employment", null);
		navigationPanel.addButton("Marketing", null);
		
		navigationPanel.setPosition(0.01f * stage.getWidth(), stage.getHeight()
				- (0.3f * stage.getHeight()));
		
		return navigationPanel;
	}
	
	/**
	 * This method creates the main section of the GUI
	 * 
	 * @return The table that was created.
	 */
	private Table createMainTable()
	{
		Table mainTable = new Table();
		mainTable.setSize(0.80f * stage.getWidth(), 0.80f * stage.getHeight());
		
		Table leftSection = new Table();
		Table rightSection = new Table();
		
		ScrollPane productGridScroll = new ScrollPane(getProductGrid(), skin);
		companyPromotionButton = new TextButton("Promote Company", skin);
		companyPromotionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (promoteCompany)
					companyPromotionButton.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				else
					companyPromotionButton.setColor(0.0f, 1.0f, 0.0f, 1.0f);
				promoteCompany = !promoteCompany;
				productChosen = false;
				if (selectedProduct != null)
					selectedProduct = null;
				if (selectedProductLabel != null)
				{
					selectedProductLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
					selectedProductLabel = null;
				}
			}
		});
		
		leftSection.add(productGridScroll).expand().fill().row();
		leftSection.add(companyPromotionButton).center().space(10.0f);
		
		ScrollPane marketingGridScroll = new ScrollPane(getMarketingGrid(), skin);
		TextButton marketinLabel = new TextButton("Select Marketing", skin);
		marketinLabel.setDisabled(true);
		marketinLabel.setColor(0.5f, 0.5f, 0.5f, 1.0f);
		TextButton nextButton = new TextButton("Next", skin);
		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (marketingMethodChosen && (productChosen || promoteCompany))
				{
					System.out.println("Moving to the next Step. "
							+ selectedMarketingMethod
							+ " will be used to market "
							+ (productChosen ? selectedProduct.getName() : "the Company."));
					game.setScreen(new MarketingWizard(game, MarketingScreen.this));
				}
			}
		});
		
		rightSection.add(marketinLabel).expand().fill().row();
		rightSection.add(marketingGridScroll).expand().fill().row();
		rightSection.add(nextButton).right().space(10.0f);
		
		mainTable.add(leftSection).expand().fill();
		mainTable.add(rightSection).expand().fill();
		
		return mainTable;
	}
	
	/**
	 * This method creates the the grid of products that the user can choose from and adds
	 * them to a table.
	 * 
	 * @return The table that was created.
	 */
	private Table getProductGrid()
	{
		Table productGrid = new Table();
		Product[] products = getProducts();
		
		for (int i = 0; i < products.length; i++)
		{
			Table newProduct = new Table();
			Label productLabel = new Label(products[i].getName(), skin);
			Image productIcon = products[i].getProductImage();
			productIcon.addListener(new productIconListener(productLabel, products[i]));
			
			newProduct.add(productIcon).size(100f).row(); // TODO change to dynamic size
			newProduct.add(productLabel);
			
			productGrid.add(newProduct).space(15);
			
			if ((i + 1) % 2 == 0 && i != products.length - 1)
				productGrid.row();
		}
		
		return productGrid;
	}
	
	/**
	 * This method creates the the grid of marketing types icons that the user can choose
	 * from and adds them to a table.
	 * 
	 * @return The table that was created.
	 */
	private Table getMarketingGrid()
	{
		Table marketingGrid = new Table();
		String[][] marketingIcons = {
				{ "Internet Campaign", "images/placeholder-Computer-Icon.png" },
				{ "Social Media", "images/placeholder-SocialMedia-icon.png" },
				{ "Television and Radio", "images/placeholder--TV-icon.png" },
				{ "Traditional Advertising", "images/placeholder-Billboard-icon.png" } };
		
		for (int i = 0; i < marketingIcons.length; i++)
		{
			Table newMarketingMethod = new Table();
			Label marketingMethodLabel = new Label(marketingIcons[i][0], skin);
			FileHandle iconLocation = Gdx.files.internal(marketingIcons[i][1]);
			Texture iconTexture = new Texture(iconLocation);
			Image marketingtIcon = new Image(iconTexture);;
			marketingtIcon.addListener(new marketingIconListener(marketingMethodLabel,
					marketingIcons[i][0]));
			
			newMarketingMethod.add(marketingtIcon).size(200f).row(); // TODO change to
																		// dynamic size
			newMarketingMethod.add(marketingMethodLabel);
			
			marketingGrid.add(newMarketingMethod).space(15);
			
			if ((i + 1) % 2 == 0 && i != marketingIcons.length - 1)
				marketingGrid.row();
		}
		
		return marketingGrid;
	}
	
	/**
	 * This method is a testing shell that creates different products and return them to
	 * be displayed.
	 * 
	 * @return an array of the products created.
	 */
	private Product[] getProducts()
	{
		// TODO get products
		Product[] products = { new Product("T-shirt 1", "default"),
				new Product("T-shirt 2", "default"), new Product("Short 1", "default"),
				new Product("Short 2", "default"), new Product("Hoodie 1", "default"),
				new Product("Pants 1", "default"), new Product("Pants 2", "default"),
				new Product("Pants 3", "default"), new Product("Pants 4", "default") };
		return products;
	}
	
	/**
	 * @return The product selected by the user.
	 */
	public Product getSelectedProduct()
	{
		return selectedProduct;
	}
	
	/**
	 * @return The marketing campaign selected by the user.
	 */
	public String getSelectedMarketingMethod()
	{
		return selectedMarketingMethod;
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
