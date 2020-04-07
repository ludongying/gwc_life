/**
 * Leaflet.draw assumes that you have already included the Leaflet library.
 */
L.drawVersion = '0.4.2';
/**
 * @class L.Draw
 * @aka Draw
 *
 *
 * To add the draw toolbar set the option drawControl: true in the map options.
 *
 * @example
 * ```js
 *      var map = L.map('map', {drawControl: true}).setView([51.505, -0.09], 13);
 *
 *      L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
 *          attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
 *      }).addTo(map);
 * ```
 *
 * ### Adding the edit toolbar
 * To use the edit toolbar you must initialise the Leaflet.draw control and manually add it to the map.
 *
 * ```js
 *      var map = L.map('map').setView([51.505, -0.09], 13);
 *
 *      L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
 *          attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
 *      }).addTo(map);
 *
 *      // FeatureGroup is to store editable layers
 *      var drawnItems = new L.FeatureGroup();
 *      map.addLayer(drawnItems);
 *
 *      var drawControl = new L.Control.Draw({
 *          edit: {
 *              featureGroup: drawnItems
 *          }
 *      });
 *      map.addControl(drawControl);
 * ```
 *
 * The key here is the featureGroup option. This tells the plugin which FeatureGroup contains the layers that
 * should be editable. The featureGroup can contain 0 or more features with geometry types Point, LineString, and Polygon.
 * Leaflet.draw does not work with multigeometry features such as MultiPoint, MultiLineString, MultiPolygon,
 * or GeometryCollection. If you need to add multigeometry features to the draw plugin, convert them to a
 * FeatureCollection of non-multigeometries (Points, LineStrings, or Polygons).
 */
L.Draw = {};

/**
 * @class L.drawLocal
 * @aka L.drawLocal
 *
 * The core toolbar class of the API — it is used to create the toolbar ui
 *
 * @example
 * ```js
 *      var modifiedDraw = L.drawLocal.extend({
 *          draw: {
 *              toolbar: {
 *                  buttons: {
 *                      polygon: 'Draw an awesome polygon'
 *                  }
 *              }
 *          }
 *      });
 * ```
 *
 * The default state for the control is the draw toolbar just below the zoom control.
 *  This will allow map users to draw vectors and markers.
 *  **Please note the edit toolbar is not enabled by default.**
 */
L.drawLocal = {
	// format: {
	// 	numeric: {
	// 		delimiters: {
	// 			thousands: ',',
	// 			decimal: '.'
	// 		}
	// 	}
	// },
	draw: {
		toolbar: {
			// #TODO: this should be reorganized where actions are nested in actions
			// ex: actions.undo  or actions.cancel
			actions: {
				title: '取消',
				text: 'cancel'
			},
			finish: {
				title: '保存',
				text: 'finish'
			},
			undo: {
				title: '删除最后一个点',
				text: 'deletenew'
			},
			buttons: {
				polyline: '线',
				polygon: '多边形',
				rectangle: '矩形',
				circle: '圆',
				marker: '标记点',
				circlemarker: 'Draw a circlemarker'
			}
		},
		handlers: {
			circle: {
				tooltip: {
					start: '点击拖动鼠标绘制'
				},
				radius: '半径'
			},
			circlemarker: {
				tooltip: {
					start: 'Click map to place circle marker.'
				}
			},
			marker: {
				tooltip: {
					start: '点击鼠标标记点'
				}
			},
			polygon: {
				tooltip: {
					start: '点击开始',
					cont: '点击继续',
					end: '点击第一个点结束'
				}
			},
			polyline: {
				error: '<strong>Error:</strong> 边界线必能交叉!',
				tooltip: {
					start: '点击开始画线',
					cont: '点击继续画线',
					end: '点击最后一个点结束画线'
				}
			},
			rectangle: {
				tooltip: {
					start: '点击拖动鼠标绘制'
				}
			},
			simpleshape: {
				tooltip: {
					end: '松开鼠标结束'
				}
			}
		}
	},
	edit: {
		toolbar: {
			actions: {
				save: {
					title: '保存',
					text: 'finish'
				},
				cancel: {
					title: '取消',
					text: 'cancel'
				},
				clearAll: {
					title: '清除全部图层',
					text: 'clearall'
				}
			},
			buttons: {
				edit: '编辑',
				editDisabled: '编辑不可用',
				remove: '删除',
				removeDisabled: '删除不可用'
			}
		},
		handlers: {
			edit: {
				tooltip: {
					text: '拖动进行编辑',
					// subtext: '编辑'
				}
			},
			remove: {
				tooltip: {
					text: '点击对象删除'
				}
			}
		}
	}
};
