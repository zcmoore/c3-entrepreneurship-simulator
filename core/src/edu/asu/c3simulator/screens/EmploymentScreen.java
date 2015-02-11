package edu.asu.c3simulator.screens;


import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

//import edu.asu.c3simulator.screens.DifficultySelectionScreen.Button2;
//import edu.asu.c3simulator.widgets.CornerAdvisor;

public class EmploymentScreen implements Screen
{
 private static class Button2 extends TextButton
 {
  Layout parent;
  
  public Button2(String text, Skin skin, Layout parent)
  {
   super(text, skin);
   this.parent = parent;
  }
  
  public float getPrefWidth()
  {
   if (parent == null)
    return super.getPrefWidth();
   else
    return parent.getPrefWidth();
  }
  
 }
 
 private static final int DESIGN_WIDTH = 1280;
 private static final int DESIGN_HEIGHT = 720;
 private static final int DESIGN_SCREEN_CENTER_X = DESIGN_WIDTH / 2;
 private static final int DESIGN_SCREEN_CENTER_Y = DESIGN_HEIGHT / 2;
 
 private Game game;
 private Skin skin;
 private Stage stage;
 
 public EmploymentScreen(Game game)
 {
  this.game = game;
  
  Viewport stageViewport = new StretchViewport(DESIGN_WIDTH, DESIGN_HEIGHT);
  this.stage = new Stage(stageViewport);
  this.skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
  
  Table roster = new Table();
  
  Actor employeePane = createEmployeePane();
  
  roster.add(employeePane).top();
  roster.add(employeePane).top().spaceLeft(75);
  
  roster.setTransform(true);
  roster.setPosition(DESIGN_SCREEN_CENTER_X, DESIGN_SCREEN_CENTER_Y);
  
  /*
   * CornerAdvisor advisor = new CornerAdvisor(ADVISOR_TEXT); float padding = 0.01f
   * * DESIGN_HEIGHT; float advisorLeft = DESIGN_WIDTH - advisor.getPrefWidth() -
   * padding; float advisorBottom = DESIGN_HEIGHT - advisor.getPrefHeight() -
   * padding; advisor.setPosition(advisorLeft, advisorBottom);
   */
  
  stage.addActor(roster);
  
 }
 
 private Actor createEmployeePane()
 {
  
  Table employeePane = new Table();
  
  List<Label> employees = new LinkedList<>();
  
  Label employee1 = new Label("Employee 1", skin);
  Label employee2 = new Label("Employee 2", skin);
  Label employee3 = new Label("Employee 3", skin);
  Label employee4 = new Label("Employee 4", skin);
  employees.add(employee1);
  employees.add(employee2);
  employees.add(employee3);
  employees.add(employee4);
  
  for (Label employee : employees)
  {
   employee.setAlignment(Align.center);
   employeePane.add(employee);
   employeePane.row();
  }
  
  return employeePane;
 }
 
 private Actor createEmploymentTitle(Layout parent, String titleText)
 {
  TextButton title = new Button2(titleText, skin, parent);
  title.align(Align.center);
  title.setDisabled(true);
  
  return title;
 }
 
 @Override
 public void render(float delta)
 {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void resize(int width, int height)
 {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void show()
 {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void hide()
 {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void pause()
 {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void resume()
 {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void dispose()
 {
  // TODO Auto-generated method stub
  
 }
 
}
