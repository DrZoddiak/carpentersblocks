package com.carpentersblocks.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.carpentersblocks.util.attribute.EnumAttributeLocation;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public class VecUtil 
{ 
	public static class Vec2l
	{
		
		private long _u;
		private long _v;
		
		public Vec2l(long u, long v)
		{
			_u = u;
			_v = v;
		}
		
		public Vec2l(EnumFacing facing, Vec3d vec3d)
		{
			switch (facing.getAxis()) 
			{
				case X:
					_u = Math.round(vec3d.zCoord * 16);
					_v = Math.round(vec3d.yCoord * 16);
					break;
					
				case Y:
	                _u = Math.round(vec3d.xCoord * 16);
	                _v = Math.round(vec3d.zCoord * 16);
					break;
					
				case Z:
					_u = Math.round(vec3d.xCoord * 16);
					_v = Math.round(vec3d.yCoord * 16);
					break;
			}
		}
		
		public long getU() 
		{
			return _u;
		}
		
		public long getV() 
		{
			return _v;
		} 
	}
	
	public static Vec2l getVec2l(EnumFacing facing, Vec3d Vec3d)
	{
		return new Vec2l(facing, Vec3d);
	}
	
	private static Vec2l[] getBoundingPlane(EnumFacing facing, Vec3d[] vecs)
	{
		long minU = 0;
		long maxU = 16;
		long minV = 0;
		long maxV = 16;
		for (Vec3d vec : vecs)
		{
			Vec2l vec2l = new Vec2l(facing, vec);
			minU = Math.min(minU, vec2l.getU());
			maxU = Math.max(maxU, vec2l.getU());
			minV = Math.min(minV, vec2l.getV());
			maxV = Math.max(maxV, vec2l.getV());
		}
		switch (facing) 
		{
			case DOWN:
				return new Vec2l[] { new Vec2l(minU, maxV), new Vec2l(minU, minV), new Vec2l(maxU, minV), new Vec2l(maxU, maxV) };
				
			case UP:
				return new Vec2l[] { new Vec2l(minU, minV), new Vec2l(minU, maxV), new Vec2l(maxU, maxV), new Vec2l(maxU, minV) };
				
			case NORTH:
				return new Vec2l[] { new Vec2l(maxU, maxV), new Vec2l(maxU, minV), new Vec2l(minU, minV), new Vec2l(minU, maxV) };
				
			case SOUTH:
				return new Vec2l[] { new Vec2l(minU, maxV), new Vec2l(minU, minV), new Vec2l(maxU, minV), new Vec2l(maxU, maxV) };
				
			case WEST:
				return new Vec2l[] { new Vec2l(minU, maxV), new Vec2l(minU, minV), new Vec2l(maxU, minV), new Vec2l(maxU, maxV) };
				
			case EAST:
				return new Vec2l[] { new Vec2l(maxU, maxV), new Vec2l(maxU, minV), new Vec2l(minU, minV), new Vec2l(minU, maxV) };
				
			default:
				return null;
		}
	}
	
	public static boolean isValid(Quad quad)
	{
		return quad != null && quad.getVecs() != null && quad.getVecs().length == 4;
	}

	private static long squareDistanceTo(EnumFacing facing, Vec2l vec2l, Vec3d Vec3d) 
	{
		Vec2l vec2l2 = new Vec2l(facing, Vec3d);
        long u = vec2l2.getU() - vec2l.getU();
        long v = vec2l2.getV() - vec2l.getV();
        return u * u + v * v;
	}

	public static Vec3d[] buildVecs(EnumFacing facing, Vec3d[] vecs) 
	{
		Set<Vec3d> set = new HashSet<Vec3d>(Arrays.asList(vecs));
		if (set.size() == 3) 
		{
			return sortTriangle(facing, vecs);
		} 
		else if (set.size() == 4)
		{
			return sortQuad(facing, vecs);
		} 
		else 
		{
			return null;
		}
	}
	
	private static Vec3d[] sortQuad(EnumFacing facing, Vec3d[] vecs) 
	{
		List<Vec3d> consumables = new LinkedList<Vec3d>(Arrays.asList(vecs));
		Vec3d[] newVecs = { vecs[0], vecs[1], vecs[2], vecs[3] };
		Vec2l[] bounds = getBoundingPlane(facing, vecs);
		for (int i = 0; i < bounds.length; ++i)
		{
			Vec3d closest = null;
			long minDist = 0;
			for (Vec3d Vec3d : consumables)
			{
				long dist = squareDistanceTo(facing, bounds[i], Vec3d);
				if (closest == null || dist < minDist)
				{
					minDist = dist;
					closest = Vec3d;
				}
			}
			consumables.remove(closest);
			newVecs[i] = closest;
		}
		return newVecs;
	}
	
	private static Vec3d[] sortTriangle(EnumFacing facing, Vec3d[] inVecs)
	{
		List<Vec3d> vecs = new ArrayList<Vec3d>(Arrays.asList(inVecs));
		List[] cornerList = 
			{
				new ArrayList<Vec3d>(),
				new ArrayList<Vec3d>(),
				new ArrayList<Vec3d>(),
				new ArrayList<Vec3d>() 
			};
		
		// Generate bounds
		Vec2l[] bounds = getBoundingPlane(facing, inVecs);

		//  If one Vec3d is closest to corner, remove corner and map it
		for (int i = 0; i < bounds.length; ++i) 
		{
			Vec3d closest = null;
			long minDist = 0;
			// Get number of matches, and closest point (single match is corner, multiple is unknown)
			for (Vec3d Vec3d : vecs)
			{
				long dist = squareDistanceTo(facing, bounds[i], Vec3d);
				if (closest == null || dist < minDist) 
				{
					minDist = dist;
					closest = Vec3d;
				}
			}
			// Add closest to corner
			cornerList[i].add(closest);
		}
		
		Vec3d[] sortedArray = new Vec3d[4];
		
		// If list has single point, assign and remove from other points
		for (int i = 0; i < cornerList.length; ++i)
		{
			List<Vec3d> list = cornerList[i];
			if (list.size() == 1) {
				sortedArray[i] = list.get(0);
				vecs.remove(sortedArray[i]);
			}
		}

		// Remaining ambiguous point should map to any unassigned corners
		for (int i = 0; i < sortedArray.length; ++i)
		{
			if (sortedArray[i] == null) 
			{
				sortedArray[i] = vecs.get(0);
			}
		}

		return sortedArray;
	}
	
	public static Vec3d getNormal(Quad quad)
	{
		Vec3d[] vecs1 = new LinkedHashSet<Vec3d>(Arrays.asList(quad.getVecs())).toArray(new Vec3d[quad.getVecs().length]);
		return (vecs1[1].subtract(vecs1[0])).crossProduct(vecs1[2].subtract(vecs1[1])).normalize();
	}
	
	public static UV[] getUV(Quad quad, boolean floatY, EnumAttributeLocation location)
	{
		if (quad.getFacing().ordinal() != location.ordinal() && !location.equals(EnumAttributeLocation.HOST)) 
		{
			return getUVSideCover(quad, floatY, location);
		} else {
			return getUV(quad, floatY);
		}
	}

	public static UV[] getUV(Quad quad, boolean floatY) 
	{
		Vec3d[] vecs = quad.getVecs();
		switch (quad.getFacing()) 
		{
    		case DOWN:
    			return new UV[] 
    			{
	    			new UV(vecs[0].xCoord, vecs[0].zCoord).invertV(),
	    			new UV(vecs[1].xCoord, vecs[1].zCoord).invertV(),
	    			new UV(vecs[2].xCoord, vecs[2].zCoord).invertV(),
	    			new UV(vecs[3].xCoord, vecs[3].zCoord).invertV()
    			};
    			
    		case UP:
				return new UV[] 
				{
    				new UV(vecs[0].xCoord, vecs[0].zCoord),
    				new UV(vecs[1].xCoord, vecs[1].zCoord),
    				new UV(vecs[2].xCoord, vecs[2].zCoord),
    				new UV(vecs[3].xCoord, vecs[3].zCoord)
    			};
				
    		case NORTH:
    			if (floatY) 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[3].xCoord, vecs[1].yCoord),
        				new UV(vecs[2].xCoord, vecs[0].yCoord),
        				new UV(vecs[1].xCoord, vecs[3].yCoord),
        				new UV(vecs[0].xCoord, vecs[2].yCoord)
        			};
    			} 
    			else 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[0].xCoord, vecs[0].yCoord).invertUV(),
        				new UV(vecs[1].xCoord, vecs[1].yCoord).invertUV(),
        				new UV(vecs[2].xCoord, vecs[2].yCoord).invertUV(),
        				new UV(vecs[3].xCoord, vecs[3].yCoord).invertUV()
        			};
    			}
    			
    		case SOUTH:
    			if (floatY) 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[0].xCoord, vecs[1].yCoord),
        				new UV(vecs[1].xCoord, vecs[0].yCoord),
        				new UV(vecs[2].xCoord, vecs[3].yCoord),
        				new UV(vecs[3].xCoord, vecs[2].yCoord)
        			};
    			} 
    			else 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[0].xCoord, vecs[0].yCoord).invertV(),
        				new UV(vecs[1].xCoord, vecs[1].yCoord).invertV(),
        				new UV(vecs[2].xCoord, vecs[2].yCoord).invertV(),
        				new UV(vecs[3].xCoord, vecs[3].yCoord).invertV()
        			};
    			}
    			
    		case WEST:
    			if (floatY) 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[0].zCoord, vecs[1].yCoord),
        				new UV(vecs[1].zCoord, vecs[0].yCoord),
        				new UV(vecs[2].zCoord, vecs[3].yCoord),
        				new UV(vecs[3].zCoord, vecs[2].yCoord)
        			};
    			} 
    			else 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[0].zCoord, vecs[0].yCoord).invertV(),
        				new UV(vecs[1].zCoord, vecs[1].yCoord).invertV(),
        				new UV(vecs[2].zCoord, vecs[2].yCoord).invertV(),
        				new UV(vecs[3].zCoord, vecs[3].yCoord).invertV()
        			};
    			}
    			
    		case EAST:
    			if (floatY) 
    			{
    				return new UV[] 
    				{
        				new UV(vecs[3].zCoord, vecs[1].yCoord),
        				new UV(vecs[2].zCoord, vecs[0].yCoord),
        				new UV(vecs[1].zCoord, vecs[3].yCoord),
        				new UV(vecs[0].zCoord, vecs[2].yCoord)
        			};
    			} 
    			else
    			{
    				return new UV[] 
    				{
        				new UV(vecs[0].zCoord, vecs[0].yCoord).invertUV(),
        				new UV(vecs[1].zCoord, vecs[1].yCoord).invertUV(),
        				new UV(vecs[2].zCoord, vecs[2].yCoord).invertUV(),
        				new UV(vecs[3].zCoord, vecs[3].yCoord).invertUV()
        			};
    			}
		}
		return null;
	}
	
	private static UV[] getUVSideCover(Quad quad, boolean floatY, EnumAttributeLocation location) 
	{
		Vec3d[] vecs = quad.getVecs();
		double depth = 0.0625D;
		switch (quad.getFacing()) 
		{
    		case DOWN:
    			switch (location)
    			{
	    			case NORTH:
	    				// Offset -Z
	    				depth = vecs[0].zCoord - vecs[1].zCoord;
	    				return new UV[] 
	    				{
	    					new UV(vecs[0].xCoord,         1.0D).invertV(),
	    					new UV(vecs[1].xCoord, 1.0D - depth).invertV(),
	    					new UV(vecs[2].xCoord, 1.0D - depth).invertV(),
	    					new UV(vecs[3].xCoord,         1.0D).invertV()
	    				};
	    				
	    			case SOUTH:
	    				// Offset +Z
	    				depth = vecs[0].zCoord - vecs[1].zCoord;
	    				return new UV[] 
	    				{
	    					new UV(vecs[0].xCoord, depth).invertV(),
	    					new UV(vecs[1].xCoord,  0.0D).invertV(),
	    					new UV(vecs[2].xCoord,  0.0D).invertV(),
	    					new UV(vecs[3].xCoord, depth).invertV()
	    				};
	    			case WEST:
	    				// Offset -X
	    				depth = vecs[3].xCoord - vecs[0].xCoord;
	    				return new UV[] 
	    				{
	    					new UV(1.0D - depth, vecs[0].zCoord).invertV(),
	    					new UV(1.0D - depth, vecs[1].zCoord).invertV(),
	    					new UV(1.0D,         vecs[2].zCoord).invertV(),
	    					new UV(1.0D,         vecs[3].zCoord).invertV()
	    				};
	    				
	    			default: //case EAST:
	    				// Offset +X
	    				depth = vecs[3].xCoord - vecs[0].xCoord;
	    				return new UV[] 
	    				{
	    					new UV( 0.0D, vecs[0].zCoord).invertV(),
	    					new UV( 0.0D, vecs[1].zCoord).invertV(),
	    					new UV(depth, vecs[2].zCoord).invertV(),
	    					new UV(depth, vecs[3].zCoord).invertV()
	    				};
    			}
    			
    		case UP:
    			switch (location) 
    			{
	    			case NORTH:
	    				// Offset -Z
	    				depth = vecs[1].zCoord - vecs[0].zCoord;
	    				return new UV[] 
	    				{
	    					new UV(vecs[0].xCoord, depth).invertV(),
	    					new UV(vecs[1].xCoord,  0.0D).invertV(),
	    					new UV(vecs[2].xCoord,  0.0D).invertV(),
	    					new UV(vecs[3].xCoord, depth).invertV()
	    				};
	    				
	    			case SOUTH:
	    				// Offset +Z
	    				depth = vecs[1].zCoord - vecs[0].zCoord;
	    				return new UV[] 
	    				{
	    					new UV(vecs[0].xCoord,         1.0D).invertV(),
	    					new UV(vecs[1].xCoord, 1.0D - depth).invertV(),
	    					new UV(vecs[2].xCoord, 1.0D - depth).invertV(),
	    					new UV(vecs[3].xCoord,         1.0D).invertV()
	    				};
	    				
	    			case WEST:
	    				// Offset -X
	    				depth = vecs[3].xCoord - vecs[0].xCoord;
	    				return new UV[] 
	    				{
	    					new UV(1.0D - depth, vecs[0].zCoord),
	    					new UV(1.0D - depth, vecs[1].zCoord),
	    					new UV(1.0D,         vecs[2].zCoord),
	    					new UV(1.0D,         vecs[3].zCoord)
	    				};
	    				
	    			default: //case EAST:
	    				// Offset +X
	    				depth = vecs[3].xCoord - vecs[0].xCoord;
	    				return new UV[] 
	    				{
	    					new UV( 0.0D, vecs[0].zCoord),
	    					new UV( 0.0D, vecs[1].zCoord),
	    					new UV(depth, vecs[2].zCoord),
	    					new UV(depth, vecs[3].zCoord)
	    				};
				}
    			
    		case NORTH:
    			switch (location) 
    			{
	    			case DOWN:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].xCoord,  0.0D).invertU(),
		    					new UV(vecs[1].xCoord, depth).invertU(),
		    					new UV(vecs[2].xCoord, depth).invertU(),
		    					new UV(vecs[3].xCoord,  0.0D).invertU()
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].xCoord,  0.0D).invertU(),
		    					new UV(vecs[1].xCoord, depth).invertU(),
		    					new UV(vecs[2].xCoord, depth).invertU(),
		    					new UV(vecs[3].xCoord,  0.0D).invertU()
		    				};
	    				}
	    				
	    			case UP:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].xCoord,  0.0D).invertU(),
		    					new UV(vecs[1].xCoord, depth).invertU(),
		    					new UV(vecs[2].xCoord, depth).invertU(),
		    					new UV(vecs[3].xCoord,  0.0D).invertU()
		    				};
	    				} 
	    				else
	    				{
		    				return new UV[] 
		    				{
	    						new UV(vecs[0].xCoord, depth).invertUV(),
		    					new UV(vecs[1].xCoord,  0.0D).invertUV(),
		    					new UV(vecs[2].xCoord,  0.0D).invertUV(),
		    					new UV(vecs[3].xCoord, depth).invertUV()
		    				};
	    				}
	    				
	    			case WEST:
	    				depth = vecs[0].xCoord - vecs[3].xCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[1].yCoord),
		    					new UV( 0.0D, vecs[0].yCoord),
		    					new UV(depth, vecs[3].yCoord),
		    					new UV(depth, vecs[2].yCoord)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[0].yCoord).invertV(),
		    					new UV( 0.0D, vecs[1].yCoord).invertV(),
		    					new UV(depth, vecs[2].yCoord).invertV(),
		    					new UV(depth, vecs[3].yCoord).invertV()
		    				};
	    				}
	    				
	    			default: //case EAST:
	    				depth = vecs[0].xCoord - vecs[3].xCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
	            				new UV(depth, vecs[1].yCoord).invertU(),
	            				new UV(depth, vecs[0].yCoord).invertU(),
	            				new UV( 0.0D, vecs[3].yCoord).invertU(),
	            				new UV( 0.0D, vecs[2].yCoord).invertU()
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(depth, vecs[0].yCoord).invertUV(),
		    					new UV(depth, vecs[1].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[2].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[3].yCoord).invertUV()
		    				};
	    				}
    			}
    			
    		case SOUTH:
    			switch (location) 
    			{
	    			case DOWN:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].xCoord,  0.0D),
		    					new UV(vecs[1].xCoord, depth),
		    					new UV(vecs[2].xCoord, depth),
		    					new UV(vecs[3].xCoord,  0.0D)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].xCoord,  0.0D),
		    					new UV(vecs[1].xCoord, depth),
		    					new UV(vecs[2].xCoord, depth),
		    					new UV(vecs[3].xCoord,  0.0D)
		    				};
	    				}
	    				
	    			case UP:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].xCoord,  0.0D),
		    					new UV(vecs[1].xCoord, depth),
		    					new UV(vecs[2].xCoord, depth),
		    					new UV(vecs[3].xCoord,  0.0D)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[]
		    						{
	    						new UV(vecs[0].xCoord, depth).invertV(),
		    					new UV(vecs[1].xCoord,  0.0D).invertV(),
		    					new UV(vecs[2].xCoord,  0.0D).invertV(),
		    					new UV(vecs[3].xCoord, depth).invertV()
		    				};
	    				}
	    				
	    			case WEST:
	    				depth = vecs[0].xCoord - vecs[3].xCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(depth, vecs[1].yCoord).invertU(),
		    					new UV(depth, vecs[0].yCoord).invertU(),
		    					new UV( 0.0D, vecs[3].yCoord).invertU(),
		    					new UV( 0.0D, vecs[2].yCoord).invertU()
		    				};
	    				} 
	    				else
	    				{
		    				return new UV[] 
		    				{
		    					new UV(depth, vecs[0].yCoord).invertUV(),
		    					new UV(depth, vecs[1].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[2].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[3].yCoord).invertUV()
		    				};
	    				}
	    				
	    			default: //case EAST:
	    				depth = vecs[0].xCoord - vecs[3].xCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
	            				new UV( 0.0D, vecs[1].yCoord),
	            				new UV( 0.0D, vecs[0].yCoord),
	            				new UV(depth, vecs[3].yCoord),
	            				new UV(depth, vecs[2].yCoord)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[0].yCoord).invertV(),
		    					new UV( 0.0D, vecs[1].yCoord).invertV(),
		    					new UV(depth, vecs[2].yCoord).invertV(),
		    					new UV(depth, vecs[3].yCoord).invertV()
		    				};
	    				}
				}
    			
    		case WEST:
    			switch (location) 
    			{
	    			case DOWN:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].zCoord,  0.0D),
		    					new UV(vecs[1].zCoord, depth),
		    					new UV(vecs[2].zCoord, depth),
		    					new UV(vecs[3].zCoord,  0.0D)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].zCoord,  0.0D),
		    					new UV(vecs[1].zCoord, depth),
		    					new UV(vecs[2].zCoord, depth),
		    					new UV(vecs[3].zCoord,  0.0D)
		    				};
	    				}
	    				
	    			case UP:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].zCoord,  0.0D),
		    					new UV(vecs[1].zCoord, depth),
		    					new UV(vecs[2].zCoord, depth),
		    					new UV(vecs[3].zCoord,  0.0D)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
	    						new UV(vecs[0].zCoord, depth).invertV(),
		    					new UV(vecs[1].zCoord,  0.0D).invertV(),
		    					new UV(vecs[2].zCoord,  0.0D).invertV(),
		    					new UV(vecs[3].zCoord, depth).invertV()
		    				};
	    				}
	    				
	    			case NORTH:
	    				depth = vecs[3].zCoord - vecs[0].zCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
	            				new UV(depth, vecs[1].yCoord).invertU(),
	            				new UV(depth, vecs[0].yCoord).invertU(),
	            				new UV( 0.0D, vecs[3].yCoord).invertU(),
	            				new UV( 0.0D, vecs[2].yCoord).invertU()
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[]
		    				{
		    					new UV(depth, vecs[0].yCoord).invertUV(),
		    					new UV(depth, vecs[1].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[2].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[3].yCoord).invertUV()
		    				};
	    				}
	    				
	    			default: //case SOUTH:
	    				depth = vecs[3].zCoord - vecs[0].zCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[1].yCoord),
		    					new UV( 0.0D, vecs[0].yCoord),
		    					new UV(depth, vecs[3].yCoord),
		    					new UV(depth, vecs[2].yCoord)
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[0].yCoord).invertV(),
		    					new UV( 0.0D, vecs[1].yCoord).invertV(),
		    					new UV(depth, vecs[2].yCoord).invertV(),
		    					new UV(depth, vecs[3].yCoord).invertV()
		    				};
	    				}
			}
    			
    		default: //case EAST:
    			switch (location)
    			{
	    			case DOWN:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].zCoord,  0.0D).invertU(),
		    					new UV(vecs[1].zCoord, depth).invertU(),
		    					new UV(vecs[2].zCoord, depth).invertU(),
		    					new UV(vecs[3].zCoord,  0.0D).invertU()
		    				};
	    				} 
	    				else
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].zCoord,  0.0D).invertU(),
		    					new UV(vecs[1].zCoord, depth).invertU(),
		    					new UV(vecs[2].zCoord, depth).invertU(),
		    					new UV(vecs[3].zCoord,  0.0D).invertU()
		    				};
	    				}
	    				
	    			case UP:
	    				depth = vecs[0].yCoord - vecs[1].yCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
		    					new UV(vecs[0].zCoord,  0.0D).invertU(),
		    					new UV(vecs[1].zCoord, depth).invertU(),
		    					new UV(vecs[2].zCoord, depth).invertU(),
		    					new UV(vecs[3].zCoord,  0.0D).invertU()
		    				};
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
	    						new UV(vecs[0].zCoord, depth).invertUV(),
		    					new UV(vecs[1].zCoord,  0.0D).invertUV(),
		    					new UV(vecs[2].zCoord,  0.0D).invertUV(),
		    					new UV(vecs[3].zCoord, depth).invertUV()
		    				};
	    				}
	    				
	    			case NORTH:
	    				depth = vecs[0].zCoord - vecs[3].zCoord;
	    				if (floatY) 
	    				{
	    					// TODO: Test and adjust
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[1].yCoord),
		    					new UV( 0.0D, vecs[0].yCoord),
		    					new UV(depth, vecs[3].yCoord),
		    					new UV(depth, vecs[2].yCoord)
		    				};
		    				
	    				} 
	    				else 
	    				{
		    				return new UV[] 
		    				{
		    					new UV( 0.0D, vecs[0].yCoord).invertV(),
		    					new UV( 0.0D, vecs[1].yCoord).invertV(),
		    					new UV(depth, vecs[2].yCoord).invertV(),
		    					new UV(depth, vecs[3].yCoord).invertV()
		    				};
	    				}
	    				
	    			default: //case SOUTH:
	    				depth = vecs[0].zCoord - vecs[3].zCoord;
	    				if (floatY) 
	    				{
		    				return new UV[] 
		    				{
	            				new UV(depth, vecs[1].yCoord).invertU(),
	            				new UV(depth, vecs[0].yCoord).invertU(),
	            				new UV( 0.0D, vecs[3].yCoord).invertU(),
	            				new UV( 0.0D, vecs[2].yCoord).invertU()
		    				};
	    				} 
	    				else
	    				{
		    				return new UV[] 
		    				{
		    					new UV(depth, vecs[0].yCoord).invertUV(),
		    					new UV(depth, vecs[1].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[2].yCoord).invertUV(),
		    					new UV( 0.0D, vecs[3].yCoord).invertUV()
		    				};
	    				}
    			}
		}
	}
	
	public static List<Quad> getPerpendicularQuads(Quad quad, double depth) 
	{
		List<Quad> list = new ArrayList<Quad>();
		Vec3d[] vecs = quad.getVecs();
		switch (quad.getFacing()) 
		{
			case DOWN:
				// NORTH
				list.add(Quad.getQuad(
						EnumFacing.NORTH,
						vecs[2].addVector(0, 0, 0),
						vecs[2].addVector(0, -depth, 0),
						vecs[1].addVector(0, -depth, 0),
						vecs[1].addVector(0, 0, 0)));
				// SOUTH
				list.add(Quad.getQuad(
						EnumFacing.SOUTH,
						vecs[0].addVector(0, 0, 0),
						vecs[0].addVector(0, -depth, 0),
						vecs[3].addVector(0, -depth, 0),
						vecs[3].addVector(0, 0, 0)));
				// WEST
				list.add(Quad.getQuad(
						EnumFacing.WEST,
						vecs[1].addVector(0, 0, 0),
						vecs[1].addVector(0, -depth, 0),
						vecs[0].addVector(0, -depth, 0),
						vecs[0].addVector(0, 0, 0)));
				// EAST
				list.add(Quad.getQuad(
						EnumFacing.EAST,
						vecs[3].addVector(0, 0, 0),
						vecs[3].addVector(0, -depth, 0),
						vecs[2].addVector(0, -depth, 0),
						vecs[2].addVector(0, 0, 0)));
				break;
		
			case UP:
				// NORTH
				list.add(Quad.getQuad(
						EnumFacing.NORTH,
						vecs[3].addVector(0, depth, 0),
						vecs[3].addVector(0, 0, 0),
						vecs[0].addVector(0, 0, 0),
						vecs[0].addVector(0, depth, 0)));
				// SOUTH
				list.add(Quad.getQuad(
						EnumFacing.SOUTH,
						vecs[1].addVector(0, depth, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[2].addVector(0, depth, 0)));
				// WEST
				list.add(Quad.getQuad(
						EnumFacing.WEST,
						vecs[0].addVector(0, depth, 0),
						vecs[0].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[1].addVector(0, depth, 0)));
				// EAST
				list.add(Quad.getQuad(
						EnumFacing.EAST,
						vecs[2].addVector(0, depth, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[3].addVector(0, 0, 0),
						vecs[3].addVector(0, depth, 0)));
				break;
			
			case NORTH:
				// DOWN
				list.add(Quad.getQuad(
						EnumFacing.DOWN,
						vecs[2].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, -depth),
						vecs[1].addVector(0, 0, -depth),
						vecs[1].addVector(0, 0, 0)));
				// UP
				list.add(Quad.getQuad(
						EnumFacing.UP,
						vecs[3].addVector(0, 0, -depth),
						vecs[3].addVector(0, 0, 0),
						vecs[0].addVector(0, 0, 0),
						vecs[0].addVector(0, 0, -depth)));
				// WEST
				list.add(Quad.getQuad(
						EnumFacing.WEST,
						vecs[3].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, -depth),
						vecs[3].addVector(0, 0, -depth)));
				// EAST
				list.add(Quad.getQuad(
						EnumFacing.EAST,
						vecs[0].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, -depth),
						vecs[0].addVector(0, 0, -depth)));
				break;
			
			case SOUTH:
				// DOWN
				list.add(Quad.getQuad(
						EnumFacing.DOWN,
						vecs[1].addVector(0, 0, depth),
						vecs[1].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, depth)));
				// UP
				list.add(Quad.getQuad(
						EnumFacing.UP,
						vecs[0].addVector(0, 0, 0),
						vecs[0].addVector(0, 0, depth),
						vecs[3].addVector(0, 0, depth),
						vecs[3].addVector(0, 0, 0)));
				// WEST
				list.add(Quad.getQuad(
						EnumFacing.WEST,
						vecs[0].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, depth),
						vecs[0].addVector(0, 0, depth)));
				// EAST
				list.add(Quad.getQuad(
						EnumFacing.EAST,
						vecs[3].addVector(0, 0, depth),
						vecs[2].addVector(0, 0, depth),
						vecs[2].addVector(0, 0, 0),
						vecs[3].addVector(0, 0, 0)));
				break;
		
			case WEST:
				// DOWN
				list.add(Quad.getQuad(
						EnumFacing.DOWN,
						vecs[2].addVector(-depth, 0, 0),
						vecs[1].addVector(-depth, 0, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, 0)));
				// UP
				list.add(Quad.getQuad(
						EnumFacing.UP,
						vecs[0].addVector(-depth, 0, 0),
						vecs[3].addVector(-depth, 0, 0),
						vecs[3].addVector(0, 0, 0),
						vecs[0].addVector(0, 0, 0)));
				// NORTH
				list.add(Quad.getQuad(
						EnumFacing.NORTH,
						vecs[0].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[1].addVector(-depth, 0, 0),
						vecs[0].addVector(-depth, 0, 0)));
				// SOUTH
				list.add(Quad.getQuad(
						EnumFacing.SOUTH,
						vecs[3].addVector(-depth, 0, 0),
						vecs[2].addVector(-depth, 0, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[3].addVector(0, 0, 0)));
				break;
			
			case EAST: // Top, Down, South render reverse
				// DOWN
				list.add(Quad.getQuad(
						EnumFacing.DOWN,
						vecs[1].addVector(0, 0, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[2].addVector(depth, 0, 0),
						vecs[1].addVector(depth, 0, 0)));
				// UP
				list.add(Quad.getQuad(
						EnumFacing.UP,
						vecs[3].addVector(0, 0, 0),
						vecs[0].addVector(0, 0, 0),
						vecs[0].addVector(depth, 0, 0),
						vecs[3].addVector(depth, 0, 0)));
				// NORTH
				list.add(Quad.getQuad(
						EnumFacing.NORTH,
						vecs[3].addVector(depth, 0, 0),
						vecs[2].addVector(depth, 0, 0),
						vecs[2].addVector(0, 0, 0),
						vecs[3].addVector(0, 0, 0)));
				// SOUTH
				list.add(Quad.getQuad(
						EnumFacing.SOUTH,
						vecs[0].addVector(0, 0, 0),
						vecs[1].addVector(0, 0, 0),
						vecs[1].addVector(depth, 0, 0),
						vecs[0].addVector(depth, 0, 0)));
				break;
		}
		return list;
	}
}