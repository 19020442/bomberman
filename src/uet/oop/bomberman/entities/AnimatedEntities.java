package uet.oop.bomberman.entities;

public abstract class AnimatedEntities extends Entity{
    public int _animate = 0;
    public void animate() {
        if (_animate < 7500 ) {
            _animate++;
        }else
            _animate = 0;
    }


}
