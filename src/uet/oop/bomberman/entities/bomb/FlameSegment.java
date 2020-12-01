package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.AnimatedEntities;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FlameSegment extends AnimatedEntities {
    boolean _last_segment;
    protected int _direction = -1;
    public int _timeAfter = 20;

    public FlameSegment(int x, int y, int direction, boolean last_segment) {
        _x = x;
        _y = y;
        _last_segment = last_segment;
        _direction = direction;
        }


    @Override
    public void update() {
        animate();
        if (_timeAfter > 0)
            _timeAfter--;
        else
            remove();
    }

    @Override
    public void render(Screen screen) {
        boolean last = is_last_segment();
        int direction = get_direction();
        int xt = (int)_x << 4;
        int yt = (int)_y << 4;
        switch (direction) {
            case 0:
                if(!last) {
//					_sprite = movingSprite(Sprite.explosion_vertical ,Sprite.explosion_vertical1 , Sprite.explosion_vertical2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical ,Sprite.explosion_vertical1 , Sprite.explosion_vertical2 , Sprite.explosion_vertical1 ,Sprite.explosion_vertical ,_animate , 20) ;
                } else {
//					_sprite = movingSprite(Sprite.explosion_vertical_top_last , Sprite.explosion_vertical_top_last1 , Sprite.explosion_vertical_top_last2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last , Sprite.explosion_vertical_top_last1 , Sprite.explosion_vertical_top_last2 ,Sprite.explosion_vertical_top_last1 ,Sprite.explosion_vertical_top_last , _animate , 20);
                }
                break;
            case 1:
                if(!last) {
//					_sprite = movingSprite(Sprite.explosion_horizontal , Sprite.explosion_horizontal1 , Sprite.explosion_horizontal2 );
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal , Sprite.explosion_horizontal1 , Sprite.explosion_horizontal2 , Sprite.explosion_horizontal1 ,  Sprite.explosion_horizontal ,  _animate , 20);
                } else {
//					_sprite = movingSprite(Sprite.explosion_horizontal_right_last , Sprite.explosion_horizontal_right_last1 , Sprite.explosion_horizontal_right_last2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last , Sprite.explosion_horizontal_right_last1 , Sprite.explosion_horizontal_right_last2,Sprite.explosion_horizontal_right_last1 ,Sprite.explosion_horizontal_right_last ,_animate , 20);
                }
                break;
            case 2:
                if(!last) {
//					_sprite = movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2,Sprite.explosion_vertical1,Sprite.explosion_vertical,_animate,20);
                } else {
//					_sprite = movingSprite(Sprite.explosion_vertical_down_last,Sprite.explosion_vertical_down_last1,Sprite.explosion_vertical_down_last2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last,Sprite.explosion_vertical_down_last1,Sprite.explosion_vertical_down_last2,Sprite.explosion_vertical_down_last1,Sprite.explosion_vertical_down_last,_animate,20);
                }
                break;
            case 3:
                if(!last) {
//					_sprite = movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1,Sprite.explosion_horizontal2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1,Sprite.explosion_horizontal2,Sprite.explosion_horizontal1,Sprite.explosion_horizontal,_animate,20);
                } else {
//					_sprite = movingSprite(Sprite.explosion_horizontal_left_last,Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last2);
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last2,Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last,_animate,20);
                }
                break;
        }

        screen.renderEntity(xt, yt , this);
    }

    @Override
    public boolean collide(Entity entity) {
        if(entity instanceof Character) {
            ((Character) entity).kill();
            return false;
        }
        return true;
    }

    public boolean is_last_segment() {
        return _last_segment;
    }

    public int get_direction() {
        return _direction;
    }
}
