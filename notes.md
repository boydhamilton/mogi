
ROTATING Axis Aligned Bounding Boxes COLLISION BREAKDOWN
- how to collide rectangles at any angle
- no longer rectangle, they are 4 lines
- find equation of each line for each rectangle (math 10 time)
- find intersection points of all lines
- if intersection point is within the bounds of one of the rectangles, they are colliding 


todo list

changing scene to be a type so we can switch scenes, + it would be nice to nest keystates and such into a class instead of having all that grotesquely on display

tags + tag collision
- parallize getentity (tag) function, doesnt have to be slow. just add a check to make sure object hasnt been added before
- hoooow ww to delete entities without collidersystem crashing. cause i can handle parallized object iteration (entity e: entities) but numbers are hard because one is a fucntion of another and it doesnt work

