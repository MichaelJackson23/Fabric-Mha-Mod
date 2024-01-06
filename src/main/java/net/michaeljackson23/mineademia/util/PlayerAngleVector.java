package net.michaeljackson23.mineademia.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

import javax.swing.text.html.parser.Entity;

public class PlayerAngleVector {
    public static Vec3d getPlayerAngleVector(LivingEntity entity, double deltax, double deltay, double deltaz) {
        Vec3d lookVec = entity.getRotationVec(1.0f);
        return new Vec3d(lookVec.x * deltax, lookVec.y * deltay, lookVec.z * deltaz);
    }
    public static Vec3d getPlayerAngleVector(LivingEntity entity, double deltax, double deltaz) {
        Vec3d lookVec = entity.getRotationVec(1.0f);
        return new Vec3d(lookVec.x * deltax, entity.getVelocity().y, lookVec.z * deltaz);
    }
}
